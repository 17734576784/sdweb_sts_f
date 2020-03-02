isql  -Usa  -P  -S%1 < createdb.sql

isql  -Usa  -P  -S%1 < paratable-new.sql
isql  -Usa  -P  -S%1 < paratable-systable.sql
isql  -Usa  -P  -S%1 < paratable-prepay.sql
isql  -Usa  -P  -S%1 < paratable-record.sql
isql  -Usa  -P  -S%1 < paratable-sts.sql
isql  -Usa  -P  -S%1 < paratable-holiday.sql

isql  -Usa  -P  -S%1 < yd_datatable.sql