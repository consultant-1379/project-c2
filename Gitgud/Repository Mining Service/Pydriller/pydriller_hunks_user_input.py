# -*- coding: utf-8 -*-
"""
Created on Tue Aug 10 11:45:23 2021

@author: ezhouoi
"""

import csv,sys, pytz
from datetime import date, datetime
from dateutil import tz
from pydriller.metrics.process.hunks_count import HunksCount

dateFromStringYear=sys.argv[1][0:4]
dateFromStringMonth=sys.argv[1][6:7]
dateFromStringDay=sys.argv[1][8:9]
print(dateFromStringYear + " " + dateFromStringMonth + " " + dateFromStringDay)
DateFromYear = int(dateFromStringYear)
DateFromMonth = int(dateFromStringMonth)
DateFromDay = int(dateFromStringDay)
dateToStringYear=sys.argv[2][0:4]
dateToStringMonth=sys.argv[2][6:7]
dateToStringDay=sys.argv[2][8:9]
print(dateToStringYear + " " + dateToStringMonth + " " + dateToStringDay)
DateToYear = int(dateFromStringYear)
DateToMonth = int(dateFromStringMonth)
DateToDay = int(dateFromStringDay)
UTC = tz.gettz('Europe/London')  
DateFrom = datetime(DateFromYear, DateFromMonth, DateFromDay, 0, 0, 0, 0, tzinfo = UTC)
DateTo = datetime(DateToYear, DateToMonth, DateToDay, 23, 59, 59, 0, tzinfo = UTC)


metric = HunksCount(path_to_repo='https://github.com/ishepard/pydriller', since=DateFrom, to=DateTo)
files = metric.count()
print("connected")
i = 0
headings = ['file_name', 'hunk_count']
print("begining to write")
with open('../project-c2/Gitgud/Repository Mining Service/REST Service/RESTEndpoint/src/main/resources/pydriller-hunks.csv', 'w', newline='', encoding='UTF8') as f:
     writer = csv.writer(f)
     writer.writerow(headings)
     print("Starting print")
     for key in files:
         writer.writerow([key, files[key]])
         i += 1
         print("printing line: " + str(i))
print(' '.join(pytz.country_timezones['gb']))    
