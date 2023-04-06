from geopy.geocoders import Nominatim
location = Nominatim(user_agent="GetLoc", timeout = 10000)
getLocation = location.geocode("15 Lo Giang 19, Hoa xuan, Cam le")
print("Address : ",getLocation.address)
print("Latitude : ",getLocation.latitude)
print("Longitude : ",getLocation.longitude)
print("Altitude : ",getLocation.altitude)