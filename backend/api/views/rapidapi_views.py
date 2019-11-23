from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from api.models import create_profile, Profile, Balance
from api.serializers import ProfileSerializer
from django.contrib.auth.models import User
import requests


@api_view(['GET'])
def show_airplane(request):
    if request.method == 'GET':
        destination = request.GET['destination']
        outboundDate = request.GET['outboundDate']
        inboundDate = request.GET['inboundDate']
        cabinClass = request.GET['cabinClass']
        url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/v1.0"

        payload = "inboundDate="+inboundDate+"&cabinClass="+cabinClass+"&country=KR&currency=KRW&locale=ko&originPlace=ICN-sky&destinationPlace="+destination+"-sky&outboundDate="+outboundDate+"&adults=1"
        headers = {
            'x-rapidapi-host': "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com",
            'x-rapidapi-key': "ec90eaf8c9mshf0e8e91adfa166cp1862f4jsnd1dc4b34e19d",
            'content-type': "application/x-www-form-urlencoded"
        }

        response = requests.request("POST", url, data=payload, headers=headers)

        url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/" + response.headers['Location'][64:]

        querystring = {"sortType": "price", "sortOrder": "asc", "pageIndex": "0", "pageSize": "10"}

        headers = {
            'x-rapidapi-host': "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com",
            'x-rapidapi-key': "ec90eaf8c9mshf0e8e91adfa166cp1862f4jsnd1dc4b34e19d"
        }

        response = requests.request("GET", url, headers=headers, params=querystring)

        legs = response.json()['Legs']
        agents = response.json()['Agents']
        itineraries = response.json()['Itineraries']
        dat = {}
        i = 0;
        for itinerarie in itineraries:
            outid = itinerarie['OutboundLegId']
            inid = itinerarie['InboundLegId']
            price = itinerarie['PricingOptions'][0]['Price']
            data = {}
            data.update({'price': price})
            agent = itinerarie['PricingOptions'][0]['Agents'][0]
            for ag in agents:
                if agent == ag["Id"]:
                    data.update({"name": ag['Name']})
                    break
            for leg in legs:
                if inid == leg['Id']:
                    data.update({'in': {'departure': leg['Departure'], 'arrival': leg['Arrival']}})
                if outid == leg['Id']:
                    data.update({'out': {'departure': leg['Departure'], 'arrival': leg['Arrival']}})
            dat.update({i: data})
            i += 1
        return Response(data=dat, status=status.HTTP_200_OK)


@api_view(['GET'])
def show_house(request):
    if request.method == 'GET':
        text = request.GET['text']
        arrival_date = request.GET['arrival_date']
        departure_date = request.GET['departure_date']
        url = "https://apidojo-booking-v1.p.rapidapi.com/locations/auto-complete"

        querystring = {"languagecode": "ko", "text": "losangeles"}

        headers = {
            'x-rapidapi-host': "apidojo-booking-v1.p.rapidapi.com",
            'x-rapidapi-key': "ec90eaf8c9mshf0e8e91adfa166cp1862f4jsnd1dc4b34e19d"
        }

        response = requests.request("GET", url, headers=headers, params=querystring)
        dest_id = response.json()[0]['dest_id']
        url = "https://apidojo-booking-v1.p.rapidapi.com/properties/list"

        querystring = {"price_filter_currencycode": "KRW", "travel_purpose": "leisure", "search_id": "none",
                       "order_by": "popularity", "languagecode": "ko", "search_type": "city", "offset": "0",
                       "dest_ids": dest_id, "guest_qty": "1", "arrival_date": arrival_date,
                       "departure_date": departure_date, "room_qty": "1"}

        headers = {
            'x-rapidapi-host': "apidojo-booking-v1.p.rapidapi.com",
            'x-rapidapi-key': "ec90eaf8c9mshf0e8e91adfa166cp1862f4jsnd1dc4b34e19d"
        }

        response = requests.request("GET", url, headers=headers, params=querystring)
