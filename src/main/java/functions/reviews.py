import json
import re
import os
import math

def getCoordinates(input):
    out1 = ""
    out2 = ""

    output = []
    for i in range(len(input)):
        if(input[i] == "l" and input [i+1] == "a" and input[i+2] == "t" and input[i+4] == "t" and input[i+7] == "e"):
            i = i + 10
            while(input[i] != ","):
                out1 += input[i]
                i += 1
            i = i + 13
            while(input[i] != ","):
                out2 += input[i]
                i += 1

    output.append(float(out1))
    output.append(float(out2))
    return output

def getCategories(input):
    output = ""
    for i in range(len(input)):
        if(input[i] == "c" and input[i+1] == "a" and input[i+3]=="e" and input[i+5] == "o" and input[i+8] == "e"):
            i = i + 13
            while(input[i] != "\""):
                output += input[i]
                i += 1
    
    return output.split(",")

def getDistance(cl1, cl2):
    if(len(cl1) != 2 or len(cl2) != 2):
        return -1

    x1 = cl1[0]
    x2 = cl2[0]
    y1 = cl1[1]
    y2 = cl2[1]

    return (math.sqrt((x2 - x1) ** 2 + (y2 - y1) ** 2))


def isRestaurant(input):

    for i in range(len(input)):
        if(i + 9 < len(input) and input[i] == "R" and input[i + 1] == "e" and input[i + 4] == "a" and input[i + 8] == "n" and input [i+9] == "t"):
            return True 
    return False


def getBusinessID(input):
    ID = ""

    for i in range(len(input)):
        if(input[i] == "b" and input[i + 8] == "_" and input[i + 10] == "d"):
            i = i + 14
            while(input[i] != "\""):
                ID += input[i]
                i += 1
    return ID;    

def getLineGivenID(list, ID):
    for i in list:
        if(getBusinessID(i) == ID):
            return(i)




r = open("data/yelp_academic_dataset_review.txt", "r")
b = open("data/smallerListOfRestaurants.txt", "r")

test = "{\"business_id\":\"BWPokhrYnWdfxdaUaM6l9Q\",\"name\":\"Nashville Symphony\",\"address\":\"1 Symphony Pl\",\"city\":\"Nashville\",\"state\":\"TN\",\"postal_code\":\"37201\",\"latitude\":36.1599355658,\"longitude\":-86.7759658783,\"stars\":4.5,\"review_count\":107,\"is_open\":1,\"attributes\":{\"Alcohol\":\"u'full_bar'\",\"RestaurantsGoodForGroups\":\"True\",\"NoiseLevel\":\"'loud'\",\"RestaurantsReservations\":\"True\",\"BusinessAcceptsCreditCards\":\"True\",\"WiFi\":\"'no'\",\"HasTV\":\"False\",\"RestaurantsPriceRange2\":\"3\",\"GoodForKids\":\"True\",\"RestaurantsDelivery\":\"False\",\"Caters\":\"False\",\"RestaurantsAttire\":\"u'dressy'\",\"OutdoorSeating\":\"True\",\"DogsAllowed\":\"False\",\"GoodForDancing\":\"False\",\"BusinessAcceptsBitcoin\":\"False\",\"BikeParking\":\"True\",\"CoatCheck\":\"True\",\"BestNights\":\"{'monday': False, 'tuesday': False, 'friday': True, 'wednesday': False, 'thursday': True, 'sunday': False, 'saturday': True}\",\"Smoking\":\"u'no'\",\"Music\":\"{'dj': False, 'background_music': False, 'no_music': False, 'jukebox': False, 'live': True, 'video': False, 'karaoke': False}\",\"RestaurantsTakeOut\":\"False\",\"WheelchairAccessible\":\"True\",\"BusinessParking\":\"{'garage': True, 'street': True, 'validated': False, 'lot': False, 'valet': True}\",\"Ambience\":\"{'touristy': False, 'hipster': False, 'romantic': False, 'divey': False, 'intimate': False, 'trendy': False, 'upscale': True, 'classy': True, 'casual': False}\"},\"categories\":\"Venues & Event Spaces, Musicians, Restaurants, Arts & Entertainment, Local Services, Nightlife, Performing Arts, Event Planning & Services, Party & Event Planning, Community Service\/Non-Profit, Music Venues\",\"hours\":{\"Monday\":\"10:0-18:0\",\"Tuesday\":\"10:0-18:0\",\"Wednesday\":\"10:0-18:0\",\"Thursday\":\"10:0-18:0\",\"Friday\":\"10:0-18:0\",\"Saturday\":\"10:0-14:0\"}}"

outputFile = open("arrayOfRestaurants.txt", "w")

outputString = "{ \"restaurantArr\": [\n"

for i in b:
    outputString += i + ","

outputString += "] }"





outputFile.write(outputString)
outputFile.close()






#Function to calculate closest business to test

# lowestDist = 10000000000
# lowestID = ""
# for i in b:
#     dist = getDistance(getCoordinates(test), getCoordinates(i))
#     if(getBusinessID(i) != getBusinessID(test) and dist < lowestDist):
#         lowestID = getBusinessID(i)
#         lowestDist = dist

    
# print(lowestID)



# for i in b:
#     if(isRestaurant(getCategories(i))):
#         outputFile.write(i)

# outputFile.close()





# outterCount = 0

# for i in r:
#     outterCount += 1
#     print(outterCount)

#     innerCount = 0
#     for j in sub:
#         innerCount += 1
#         if(innerCount % 69902 == 0):
#             print(innerCount / 6990280 * 100)

#         if(getBusinessID(j) == getBusinessID(i)):
#             outputFile.write(j + ",\n")
#             print(getBusinessID(j))
    


# outputFile.write("] }") 
# outputFile.close()
# f.close()