#!/bin/bash

#This works

PGP_PASSWORD="idqaatcveNof7KOWGP7i"
PASS_PHRASE_PATH = $1
NEW_FILE_NAME = $2 
OLD_FILE_NAME = $3


yes $PGP_PASSWORD | gpg --batch --passphrase-file $PASS_PHRASE_PATH --armor --encrypt --recipient DIRECTLINK-to-NAB --sign --local-user PPS_MUTUAL_PROD --output $NEW_FILE_NAME $OLD_FILE_NAME