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
 
 
 
Thread Related Changes :
 
I have used read/write lock to make this code thread safe.
 
All the get methods will have read access , as it won't change any data and hence it won't corrupt our data when executed in   multithreaded environment.

All add and update methods have write access , as if 2 or more threads execute them simultaneously there can be a race condition and our data can be corrupted and we may not end up with correct result.
 
 
Advantages:

#At a time, there can be many threads running with read lock concurrently but only one thread can acquire write lock. Hence safeguarding our data.

#It saves time if many threads wants to access shared data with read access only as we are only waiting when there is a thread executing with write lock.

#ReentrantReadWriteLocks improves concurrency when collections are expected to be large or accessed by more reader threads than writer threads.

#ReentrantReadWriteLocks maintains faireness , as in it will give the write access to the thread whose waiting time is longest so it prevents starvation(which is possible when you use synchronize keyword).

#ReentrantLock is more scalable, perform much better under higher contention. 

#ReentrantLock has method lockInterruptibly() to intrupt a thread execution going on , which can be used in some cases.

#ReentrantLock has method to get the number of holds on  write lock by the current thread.


Cons 

#It basically implemented by programmer , so there are chances of mistakes like forgetting to unlock the lock.

#It's complicated and should be used only when there are significant number of read operation so that they can be executed concurrently.

#It is slower to aquire lock as compare to acquire a simple mutex.


 
