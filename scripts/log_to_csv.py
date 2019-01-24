import csv
import datetime
import sys

try:

  build=sys.argv[1]
except:
  print("Please Provide the build number also")
  exit()
simulation_reader=open(r'simulation.log','r')
trend=open('simulation.csv','wb')
reader = csv.reader(simulation_reader,delimiter='\t')
writer=csv.writer(trend, delimiter=',')
names=['timeStamp','elapsed','label','responseCode','responseMessage','threadName','dataType','success','bytes','grpThreads','allThreads','URL','Latency','SampleCount','ErrorCount','Hostname','IdleTime','Connect','BUILD_NUMBER']
writer = csv.DictWriter(trend, fieldnames=names)
writer.writeheader()
t=[]
for li in reader:
	if li[0]=='GROUP' and li[7]=='OK':
		#response_time=(int(li[6])-int(li[5]))
		Start=str(li[4])
		End=str(li[5])
		writer.writerow({'timeStamp':Start,'elapsed':li[6],'label':li[3],'responseCode':'200','responseMessage':'','threadName':'','dataType':'','success':'true','bytes':'','grpThreads':'','allThreads':'','URL':'','Latency':'','SampleCount':'1','ErrorCount':'0','Hostname':'','IdleTime':'','Connect':'','BUILD_NUMBER':build})
	elif li[0]=='GROUP':
		#response_time=(int(li[6])-int(li[5]))
		Start=str(li[4])
		End=str(li[5])
		writer.writerow({'timeStamp':Start,'elapsed':li[6],'label':li[3],'responseCode':'Not 200','responseMessage':'','threadName':'','dataType':'','success':'false','bytes':'','grpThreads':'','allThreads':'','URL':'','Latency':'','SampleCount':'1','ErrorCount':'1','Hostname':'','IdleTime':'','Connect':'','BUILD_NUMBER':build})