from geopy.geocoders import Nominatim
location = Nominatim(user_agent="GetLoc", timeout = 10000)
getLocation = location.reverse('16.0425714,108.219962')
print("Address : ",getLocation.address)
print("Latitude : ",getLocation.latitude)
print("Longitude : ",getLocation.longitude)
print("Altitude : ",getLocation.altitude)