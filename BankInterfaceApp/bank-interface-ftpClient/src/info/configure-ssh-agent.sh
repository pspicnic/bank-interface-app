#!/bin/bash
# SFTP Connection
#@Author : Lex Singatha 
#@Email  : s.singatha@gmail.com


PID=$(pgrep -n ssh-agent)
echo "========== Process to kill =========="
echo $PID

if [ ! -z $PID -a $PID != " " ]
then

    USER=$(ps -u -p $PID)

    if  echo $USER | grep -q "root"
    then 
  	echo "This is root Process"
  	eval $(ssh-agent)	
    else 
    	if [ -z $PID ]
  	then
      	    echo "The Process ID is empty.... now starting new Process" 
     	    eval $(ssh-agent)  
  	else
	    echo "Killing existing Process and Starting a new one"
     	    kill $PID
       	    eval $(ssh-agent)
     	    echo "New Process is running"
        fi
    fi
else
    eval $(ssh-agent)
fi

PASS="thisisthebankapp"
install -vm700 <(echo "echo $PASS") "$PWD/ps.sh"
cat ~/.ssh/BankInterfaceTestKeys-keys | SSH_ASKPASS="$PWD/ps.sh" ssh-add - && rm -v "$PWD/ps.sh"

#/home/custadmin/.ssh



