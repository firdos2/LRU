# LRU
#framework used dropWizard 
API's : 

#1 to get value By key 

Get method -  http://localhost/8080/Lru/{key}
              Example :http://localhost/8080/Lru/1 where 1 is the key(type Integer)
              it will return the value of Key if that key exist 
              it will return -1 , when key doesn't exist
              
#2 to put key and value pair 

POST method -  http://localhost/8080/Lru/setData/{key}/{value}
              Example :http://localhost/8080/Lru/setData/1/26 
              where 1 is the key(type Integer) and 26 is value (type Integer);
              it will return the key in response when that key is added.
              
I have assumed the capacity as 4

test cases :

1:
 put (1,2)
 put(2,3)
 put (3,4)
 put(4,5)
 put(5,6)
if now we will run the first API : to get value of key 1 , we won't get it as it was removed when 5,6 was inserted as 1,2 was least recently used and capacity was full.

2:
 put(1,2)
 put(2,3)
 put(3,4)
 put(1,5)
 put(4,7)
 put(5,8)
 
 if now we will run the first API : to get value of key 1 , we will get it but (2,3) will be removed as it is least recently used
 
 #commands
 
 mvn clean install ( in LRU package)
 
 java -jar target/LRUCache-0.0.1-SNAPSHOT.jar server
 
 now execute the API's 
 
 
