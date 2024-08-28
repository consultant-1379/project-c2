# -*- coding: utf-8 -*-
"""
Created on Fri Aug 13 11:42:25 2021

@author: ezhouoi
"""

# -*- coding: utf-8 -*-
"""
Created on Mon Aug  9 15:10:15 2021

@author: ezhouoi
"""
import csv, time, sys
from pydriller.metrics.process.hunks_count import HunksCount
start_time=time.time()

print("connecting to Repo")
metric = HunksCount(path_to_repo='https://github.com/ishepard/pydriller', from_commit=sys.argv[1], to_commit=sys.argv[2])
files = metric.count()
print("connected")
i=0
headings = ['file_name', 'hunk_count']
with open('../project-c2/Gitgud/Repository Mining Service/REST Service/RESTEndpoint/src/main/resources/pydriller-hunks.csv', 'w', newline='', encoding='UTF8') as f:
    print("begining to write")
    writer = csv.writer(f)
    writer.writerow(headings)
    for key in files:
        writer.writerow([key, files[key]])
        i += 1
        print("printing line: " + str(i))
end_time=time.time()
print("Time taken =", end_time-start_time)
print("done")

