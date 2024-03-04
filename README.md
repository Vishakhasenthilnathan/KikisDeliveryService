KikisDeliveryService
Command Line Application for a small scale courier service

## Tests

Test files have been added and compared to verify the results

[COMMAND FILE](src/test/resources/test_kiki_delivery_service.txt)

[EXPECTED](src/test/resources/test_kiki_delivery_service_expected_output.txt)

[ACTUAL](src/test/resources/test_kiki_delivery_service_actual_output.txt)

# Problem1 
You are required to build a command line application to estimate the total delivery cost of each package with an offer code (if applicable).

Note: Code should be extensible & scalable for more offer codes.

Delivery Cost = Base Delivery Cost + (Package Total Weight * 10) + (Distance to Destination * 5)

#### Input Formats

base_delivery_cost no_of_packges

pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1

...


#### Output Format

pkg_id1 discount1 total_cost1

...

#### Sample (I/P)

100 3

PKG1 5 5 OFR001

PKG2 15 5 OFR002

PKG3 10 100 OFR003


#### Sample (O/P)

PKG1 0 175

PKG2 0 275

PKG 35 665



# Problem 2
You are required to build a command line application to calculate the estimated delivery time for every package by maximizing no. of packages in every shipment.

#### Input Formats
base_delivery_cost no_of_packges

pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1

....


no_of_vehicles max_speed max_carriable_weight


#### Output Format
pkg_id1 discount1 total_cost1 estimated_delivery_time1_in_hours

...



#### Sample (I/P)

100 5

PKG1 50 30 OFR001

PKG2 75 125 OFFR0008

PKG3 175 100 OFFR003

PKG4 110 60 OFFR002

PKG5 155 95 NA

2 70 200

#### Sample (O/P)

PKG1 0 750 6.82

PKG2 0 1475 1.78

PKG3 0 2350 1.42

PKG4 105 1395 0.85

PKG5 0 2125 4.18


