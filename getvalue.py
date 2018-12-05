#coding=utf-8
#python 从json中提取值，或从html中获取
import json
import sys
import re
import bs4
import html


def findvalue(str1, index):
    findpattern = r"\((.*)\)"
    searchobj = re.match(findpattern, index)
    if searchobj:
        if isinstance(str1,str):
            str1=json.loads(str1)
        if isinstance(str1,list):
            lst=[]
            condition = searchobj.group(1)
            condpattern = r"(\S+)\.(\S+)\((\S+)\)"
            searchcond = re.findall(condpattern, condition)
            if searchcond:
                for i in range(len(str1)):
                    value=str1[i]
                    valuelist=[]
                    for j in range(len(searchcond)):
                        label=searchcond[j][0]
                        methodname=searchcond[j][1]
                        params=searchcond[j][2]
                        valuelist.append(domethod(value,label,methodname,params))
                    if combinecond(condition,valuelist):
                        lst.append(value)
            return lst
    else:
        return getvalue(index, str1)

def combinecond(str,valuelist):
    pattern = r"\s+(and|or)\s+"
    searchobj=re.findall(pattern,str)
    result = valuelist[0];
    if searchobj:

        for i in range(len(searchobj)):
            if searchobj[i]=="and":
                result=result and valuelist[i+1]
            elif searchobj[i] =="or":
                result = result or valuelist[i+1]
    return result

#调用方法
def domethod(value,label,methondname,params):
    if methondname=="contains":
        return params in value[label]
    elif methondname=="isgreater":
        return value[label]>params
    elif methondname=="islesser":
        return value[label]<params
    elif methondname=="equals":
        return str(value[label])==params
    elif methondname=="islesserEqual":
        return value[label]<=params
    elif methondname=="isgreaterEqual":
        return value[label]>=params
    else:
        pass
        #print("unkown method cant do")

def getvalue(index, str1):
    if isinstance(str1,str):
        str1=json.loads(str1)
    if str(index).isdigit():
        index = int(index)
    else:
        index = str(index)
    return str1[index]

def getjsonvalue(jsondata, valuepath):
    data = json.loads(jsondata)
    # print(data)
    path = str.split(valuepath, "/")
    if len(path) == 0 or valuepath.strip() == "" or valuepath == "/":
        return jsondata
    result = data
    for obj in range(len(path)):
        result=findvalue(result,path[obj])
    return result
def gethtmlvalue(htmldata,selectpath):
    soup=bs4.BeautifulSoup(htmldata,"lxml")
    path = str.split(selectpath, "/")
    if len(path) == 0 or selectpath.strip() == "" or selectpath == "/":
        return htmldata
    result=soup;
    pattern=r"([a-zA-Z0-9]+)\[(.*)\]"
    for i in range(len(path)):
        searchobj = re.findall(pattern, path[i])
        if searchobj:
            for j in range(len(searchobj)):
                if searchobj[j][1].isdigit():                  
                    result = result.select_one(searchobj[j][0]+":nth-of-type("+str(searchobj[j][1])+")")
                else:
                    result=result.select_one(searchobj[j][0])[searchobj[j][1]]
        else:
            result = result.select_one(path[i])   
    if isinstance(result,str):       
        return result
    else:
        return result.string
def readFile(path):
    file=open(path,encoding="utf-8")
    str=file.read();
    return str
if __name__=="__main__":
    type=sys.argv[1]
    jsondata=readFile(sys.argv[2])
    valuepath=sys.argv[3]
    #jsondata=jsondata.replace("\"{","{").replace("}\"","}").replace("\"[","[").replace("]\"","]").replace("\"\"","\"")
    if(type=="json"):
      print(getjsonvalue(jsondata,valuepath))
    elif(type=="html"):
      print(gethtmlvalue(jsondata,valuepath))