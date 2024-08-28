# -*- coding: utf-8 -*-
"""
Created on Wed Aug 11 09:34:20 2021

@author: ezhouoi
"""
import os

start="C:/Users/ezhouoi/Desktop/project-C2/project-c2/Gitgud/Repository Mining Service/Pydriller"
path = "C:/Users/ezhouoi/Desktop/project-C2/project-c2/Gitgud/Repository Mining Service/REST Service/RESTEndpoint/src/main/resources"
relative_path = os.path.relpath(path, start)
print(relative_path)