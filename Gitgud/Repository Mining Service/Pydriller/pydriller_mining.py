# -*- coding: utf-8 -*-
"""
Created on Mon Aug  9 11:24:15 2021

@author: ezhouoi
"""
import csv, os
from pydriller import Repository

i = 0
headings = ['commit_Hash_id', 'contributor_name', 'contributor_email', 'files_changed', 'insertions', 'deletions', 'date_submitted']
print("connecting to Repo")
with open('../project-c2/Gitgud/Repository Mining Service/REST Service/RESTEndpoint/src/main/resources/pydriller.csv', 'w', newline='', encoding='UTF8') as f:
    print("connected")
    writer = csv.writer(f)
    writer.writerow(headings)
    print("begining to write")
    for commit in Repository('https://github.com/ishepard/pydriller').traverse_commits():
        datestring = str(commit.committer_date)
        datestring =datestring[:9]+datestring[24:]
        writer.writerow([str(commit.hash), str(commit.committer.name), str(commit.committer.email), str(commit.insertions), str(commit.deletions), str(commit.files), datestring])
        i += 1
        if(i % 25 == 0):
           print("printing line: " + str(i))