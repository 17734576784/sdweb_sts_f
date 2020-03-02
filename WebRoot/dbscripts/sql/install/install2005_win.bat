osql  -Usa  -P  -S%1 < createdb.sql

osql  -Usa  -P  -S%1 < paratable-new.sql
osql  -Usa  -P  -S%1 < paratable-systable.sql
osql  -Usa  -P  -S%1 < paratable-prepay.sql
osql  -Usa  -P  -S%1 < paratable-record.sql
osql  -Usa  -P  -S%1 < paratable-sts.sql
osql  -Usa  -P  -S%1 < paratable-holiday.sql

osql  -Usa  -P  -S%1 < yd_datatable.sql