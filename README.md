# NYC TLC Trip Data API

This web service API provides a list of 2018 New York City Taxi transportation trips occurred in January.  There are three different vehicle type data currently available.  They are the following:

* FHV - For-Hire Vehicles
* YELLOW - Yellow taxi
* GREEN - Green Cab

Some of the information you can find about the trips are the start and end locations as well as the pick up and drop off times.  For yellow taxi and green cab trips, you will find more interesting data such as the number of passengers for each trip, fare amount, tax, tip, total paid and also payment types.

## Build Project with Maven
To be able to run the service you will need to build it first.  To build and package into a single executable Jar file with a Maven, use the below command.  You need to run it from the project folder that contains the pom.xml.

`maven package` 

or 

`maven install`

## Run the Service 
To run this application from command line, you can use the jar -jar command.
`java - jar target\trip-csv-api-0.0.1.jar`

## Test the Service

You may use this API service to find a list of trips based on the start/end borough within a time range.  For example, to obtain a list of For-Hire vehicle trips from Queens to Manhattan from 1/9 to 1/10, provide the following input:
* vehicle: FHV
* startBorough: Queens
* toBorough: Manhattan
* fromTime: 2018-01-09T07:00:00
* toTime: 2018-01-10T07:00:00

Note: date and time must be provided in the format of `yyyy-MM-dd'T'HH:mm:ss` as provided below.

### Find All Green Cab Trips in Brooklyn between 7AM and 12PM on 1/1

`curl -v "http://localhost:8080/trips?vehicle=green&fromBorough=Brooklyn&toBorough=Brooklyn&fromTime=2018-01-01T07:00:00&toTime=2018-01-01T12:00:00"`

You will receive the JSON response.  `Total` in the response shows the total number of trips found for this search.

```json
{"total":465,"tripResults":[{"pickUpLoc":80,"dropOffLoc":80,"pickUpTime":"2018-01-01 07:22:18","dropOffTime":"2018-01-01 07:23:18","vendorId":"1","storeFwdFlag":"N","rateCode":"5","distance":0.1,"fareAmt":0.0,"extraAmt":0.0,"mtaTax":0.0,"tip":0.0,"toll":0.0,"impSurcharge":0.0,"totalAmt":0.0,"paymentType":3,"ehailFee":null,"tripType":2,"conSurcharge":null,"passengerCnt":1},{"pickUpLoc":80,"dropOffLoc":80,"pickUpTime":"2018-01-01 07:23:52","dropOffTime":"2018-01-01 07:24:40","vendorId":"1","storeFwdFlag":"N","rateCode":"5","distance":0.1,"fareAmt":25.0,"extraAmt":0.0,"mtaTax":0.0,"tip":2.0,"toll":0.0,"impSurcharge":0.0,"totalAmt":27.0,"paymentType":1,"ehailFee":null,"tripType":2,"conSurcharge":null,"passengerCnt":1}]...}
```

Limitation: This service is designed to return 50 trip details at a time.  In order to retrieve the latter trips, you may provide an offset value, ex. offset=0 for the first 50 trip data, offset=1 for the next 50 trips and so on.  Below is an example on how to use the service with an offset.

`curl -v "http://localhost:8080/trips?vehicle=green&fromBorough=Brooklyn&toBorough=Brooklyn&fromTime=2018-01-01T07:00:00&toTime=2018-01-01T12:00:00&offset=5"`


## Errors

Error may occur when you provide invalid search keyword or when the given option is not found.  It is also possible that the provided search data is not in the expected format, such as invalid date/time format.  Below is a list of possible errors you may receive.

### Invalid Vehicle Type

`curl -v "http://localhost:8080/trips?vehicle=f&fromBorough=test&toBorough=Manhatten&fromTime=2018-01-09T07:00:00&toTime=2018-01-10T07:00:00"`

Response you will receive:

`{"status":"404 NOT_FOUND","message":"Invalid vehicle type f ! Valid vehicles are FHV, YELLOW and GREEN.","time":"Mon Apr 12 17:19:39 EDT 2021"}`

### Invalid Borough

`curl -v "http://localhost:8080/trips?vehicle=green&fromBorough=Boston&toBorough=Brooklyn&fromTime=2018-01-01T07:00:00&toTime=2018-01-01T12:00:00&offset=5"`

Response you will receive:

`{"status":"404 NOT_FOUND","message":"Boston Not Found","time":"Mon Apr 12 18:08:49 EDT 2021"}`


## Disclaimer

This service is nearly from perfect.  It was developed under a short period of time.  I am currently working on adding additional features and improvement for a better and faster service that can retrieve and return a large number of trip data. Feel free to test this service!  I would love to hear your thoughts!

Thanks,

Fiona
