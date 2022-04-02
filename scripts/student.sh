#!/usr/bin/python3.6
###############################################################################
###############################################################################
__student_REQUEST(){
URI='http://localhost:8081/student'
RESPONSE=${@}
CNTR=0
if [ "${#}" -eq 1 ]; then
   # printf "\n${RESPONSE[*]}\n"
   case ${@} in
      y|yes|Y|Yes|YES)
         __student_REQUEST
      ;;
      n|no|N|No|NO)
         exit $?
      ;;
      *)
         exit $?
      ;;
   esac
elif [ "${#}" -gt 1 ]; then
   for item in ${@}; do
      printf "${item}\n"
   done
elif [  "${#}" -eq 0 ]; then
   MESSAGE="""Select METHOD: [ [POST]|[GET]|[UPDATE|PUT]|[DELETE] ]:  """
   REPEAT="""Try Again: [ [(y)es] | [(n)o] ]:  """
   echo ""
   read -p "${MESSAGE}" RESPONSE 
   echo ""
   case "${RESPONSE}" in
#  ###############################################################################
   # # POST Request: insert an entity
   p|P|post|Post|POST)
      # read -p "${MESSAGE}" RESPONSE 
      RESPONSE=$(curl -o - -i -X POST "${URI}/register/" \
         -H 'Content-Type: application/json' \
         -d '{"name":"Dellius","email":"dellius@gmail.com","dob":"1999-05-18","major":"BIT"}' )



   ;;
   # ###############################################################################
   # GET Request: retrieve an entity
   get|GET)
      # read -p "${MESSAGE}" RESPONSE 
      RESPONSE=$(curl -o - -i -X GET "${URI}/findByName/bob" \
         -H 'Content-Type: application/json')
   ;;
   ###############################################################################
   # PUT Request: update an entity
   update|UPDATE|put|PUT)
      read -rp "Provide: ID => " ID
      # read -rp "Provide: name=<name> => " NAME 
      # read -rp "Provide: email=<email> => " EMAIL 
      printf "${NAME} & ${EMAIL}"
      RESPONSE=$(curl -o - -i -X PUT "${URI}/update/${ID}?name=Billy&billy@gmail.com" \
         -H 'Content-Type: application/json')
   ;;
   # ###############################################################################
   # DELETE Request: delete an entity
   delete|DELETE)
      read -p "Provide studentId for deletion: " RESPONSE 
      RESPONSE=$(curl -o - -i -X DELETE "${URI}/delete/${RESPONSE}" \
         -H 'Content-Type: application/json')
   ;;
   *)
      __student_REQUEST 
   ;;
   ###############################################################################
#  ###############################################################################
esac
printf "\n${RESPONSE}\n"
echo ""
read -p "${REPEAT}" RESPONSE
echo ""
__student_REQUEST "${RESPONSE[@]}"
elif [ "${CNTR}" -eq 2 ]; then
   echo "$?"
   exit 0
fi;
exit $?
}
__student_REQUEST
