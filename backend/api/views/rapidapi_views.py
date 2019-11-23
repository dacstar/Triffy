from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from api.models import  Airplane, Hotel
from django.contrib.auth.models import User
from api.serializers import AirplaneSerializer, HotelSerializer
import requests, datetime


#스카이스캐너에서 예약할 항공권 리스트를 뿌려줌
@api_view(['POST'])
def show_airplane(request):
    if request.method == 'POST':
        destination = request.POST['destination']
        outboundDate = request.POST['outboundDate']
        inboundDate = request.POST['inboundDate']
        cabinClass = request.POST['cabinClass']
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
        dat = []
        for itinerarie in itineraries:
            outid = itinerarie['OutboundLegId']
            inid = itinerarie['InboundLegId']
            price = itinerarie['PricingOptions'][0]['Price']
            data = {}
            data.update({'price': price})
            agent = itinerarie['PricingOptions'][0]['Agents'][0]
            for ag in agents:
                if agent == ag["Id"]:
                    data.update({"name": ag['Name'], "img": ag['ImageUrl']})
                    break
            for leg in legs:
                if inid == leg['Id']:
                    data.update({'indeparture': leg['Departure'], 'inarrival': leg['Arrival']})
                if outid == leg['Id']:
                    data.update({'outdeparture': leg['Departure'], 'outarrival': leg['Arrival']})
            dat.append(data)
        return Response(data=dat, status=status.HTTP_200_OK)
    # 스카이스캐너로 한 항목 선택을 하면 데이터베이스에 저장


@api_view(['POST'])
def save_airline(request):
    if request.method == 'POST':
        user_id = request.POST['user_id']
        user = User.objects.get(username=user_id)
        name = request.POST['name']
        price = float(request.POST['price'])
        outdeparture = request.POST['outdeparture']
        indeparture = request.POST['indeparture']
        outarrival = request.POST['outarrival']
        inarrival = request.POST['inarrival']
        Airplane(price=price, user_id=user, outdeparture=outdeparture, indeparture=indeparture, outarrival=outarrival, inarrival=inarrival, name=name).save()
        return Response(status=status.HTTP_201_CREATED)


@api_view(['POST'])
def show_house(request):
    if request.method == 'POST':
        text = request.POST['text']
        arrival_date = request.POST['arrival_date']
        departure_date = request.POST['departure_date']
        url = "https://apidojo-booking-v1.p.rapidapi.com/locations/auto-complete"

        querystring = {"languagecode": "ko", "text": text}

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
        results = response.json()['result']
        data=[]
        for result in results:
            dat={}
            dat.update({'photo_url':result['main_photo_url'],
                        'min_price': result['min_total_price'],
                        'review_score': result['review_score'],
                        'hotel_name': result['hotel_name_trans']})
            data.append(dat)

        return Response(data=data, status=status.HTTP_200_OK)


@api_view(['POST'])
def save_house(request):
    if request.method == 'POST':
        user_id = request.POST['user_id']
        user = User.objects.get(username=user_id)
        name = request.POST['name']
        price = float(request.POST['price'])
        departure = request.POST['departure']
        arrival = request.POST['arrival']
        Hotel(price=price, user_id=user, departure=departure, arrival=arrival, name=name).save()
        return Response(status=status.HTTP_201_CREATED)


@api_view(['POST'])
def show_reserve_airline(request):
    if request.method == 'POST':
        user_id = request.POST['user_id']
        user = User.objects.get(username=user_id)
        date = datetime.date.today()
        airline = list(Airplane.objects.filter(user_id=user).filter(outdeparture__gt=date).order_by('-id'))[0]
        serializer = AirplaneSerializer(airline)
        return Response(data=serializer.data, status=status.HTTP_200_OK)


@api_view(['POST'])
def show_reserve_hotel(request):
    user_id = request.POST['user_id']
    user = User.objects.get(username=user_id)
    date = datetime.date.today()
    hotel = list(Hotel.objects.filter(user_id=user).filter(arrival__gt=date).order_by('-id'))[0]
    serializer = HotelSerializer(hotel)
    return Response(data=serializer.data, status=status.HTTP_200_OK)