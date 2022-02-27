#!/usr/bin/env bash
###############################################################################
###############################################################################
__student_REQUEST(){
URI='http://localhost:8081/api/v1/student'
RESPONSE=(${@})
CNTR=0
if [ "${#}" -eq 1 ]; then
   printf "\n${RESPONSE[*]}\n"
elif [ "${#}" -gt 1 ]; then
   for item in (${@}); do
      printf "${item}\n"
   done
elif [  "${#}" -eq 0 ]; then
   MESSAGE=(
   """
   Select METHOD: [ POST, GET, UPDATE|PUT, DELETE ]:    
   """
   )
   read -p "${MESSAGE}" RESPONSE 
   case "${RESPONSE}" in
#  ###############################################################################
   # # POST Request: insert an entity
   post|POST)
      # read -p "${MESSAGE}" RESPONSE 
      RESPONSE=$(curl -o - -i -X POST "${URI}/new" \
         -H 'Content-Type: application/json' \
         -d '{"name":"Dellius","email":"dellius@gmail.com","dob":"1999-05-18","major":"BIT"}')
      printf "\n${RESPONSE}\n"
   ;;
   # ###############################################################################
   # GET Request: retrieve an entity
   get|GET)
      # read -p "${MESSAGE}" RESPONSE 
      RESPONSE=$(curl -o - -i -X GET "${URI}/find/bob" \
         -H 'Content-Type: application/json')
      printf "\n${RESPONSE}\n"
   ;;
   ###############################################################################
   # PUT Request: update an entity
   update|UPDATE|put|PUT)
      # read -p "${MESSAGE}" RESPONSE 
      RESPONSE=$(curl -o - -i -X PUT "${URI}/update/2?name=Joseph&email=joseph@gmail.com" \
         -H 'Content-Type: application/json')
      printf "\n${RESPONSE}\n"
   ;;
   # ###############################################################################
   # DELETE Request: delete an entity
   delete|DELETE)
      # read -p "${MESSAGE}" RESPONSE 
      RESPONSE=$(curl -o - -i -X DELETE "${URI}/delete/2" \
         -H 'Content-Type: application/json')
      printf "\n${RESPONSE}\n"
   ;;
   *)
      __student_pgud "\n${RESPONSE[@]}\n"
   ;;
   ###############################################################################
#  ###############################################################################
esac
elif [ "${CNTR}" -eq 2 ]; then
   echo "$?"
   exit 0
fi;
exit $?
}