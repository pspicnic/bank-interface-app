#!/usr/bin/expect

set timeout 20

set working_dir [lindex $argv 0]
set filename    [lindex $argv 1]

spawn sftp -oPORT=9022 -oIdentityFile=~/Bank-Interface-TEST-key -oPasswordAuthentication=no NOAK01AU@test-sftp.nabmarkets.com

match_max 10000

expect -exact "Connecting to test-sftp.nabmarkets.com...\r
FIS Gateware SFTP Server 3.1.3\r
Copyright (c) 2009-2016 FIS Australasia Pty Limited. All rights reserved.\r
Welcome to the Gateware SFTP server.\r
Enter passphrase for key '/root/Bank-Interface-TEST-key': "

send -- "1NaTvwLM3U19RCbdqPFx\r"

expect "sftp>"

send "cd DTDD\r"

expect "sftp>"

send "put $filename"

expect "Uploading $filenameto /DTDD/$filename"

#expect "sftp>"

send "exit"

expect eof

