#!/usr/bin/env bash
###############################################################################
###############################################################################
# # POST Request: insert an entity
RESPONSE=$(curl -o - -i -X POST "http://localhost:8081/api/v1/student/new" \
   -H 'Content-Type: application/json' \
   -d '{"name":"Dellius","email":"dellius@gmail.com","dob":"1999-05-18","major":"BIT"}')
printf "\n${RESPONSE}\n"
# ###############################################################################
# # GET Request: retrieve an entity
# RESPONSE=$(curl -o - -i -X GET "http://localhost:8081/api/v1/student/find/bob" \
#    -H 'Content-Type: application/json')
# printf "\n${RESPONSE}\n"
# ###############################################################################
# # DELETE Request: delete an entity
# RESPONSE=$(curl -o - -i -X DELETE "http://localhost:8081/api/v1/student/delete/2" \
#    -H 'Content-Type: application/json')
# printf "\n${RESPONSE}\n"
###############################################################################
# # PUT Request: update an entity
# RESPONSE=$(curl -o - -i -X PUT "http://localhost:8081/api/v1/student/update/2?name=Joseph&email=joseph@gmail.com" \
#    -H 'Content-Type: application/json')
# printf "\n${RESPONSE}\n"
###############################################################################