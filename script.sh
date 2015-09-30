#! /bin/bash                                                                  
                                                                              
echo checkpage swap ========================                                  
cd /etc/nginx/sites-available/                                                
mv kuku originkuku                                                            
mv checkkuku kuku                                                             
                                                                              
echo nginx reload ==========================                                  
service nginx reload                                                          
                                                                              
echo tomcat shutdown =======================                                  
cd /home/kuku/apache-tomcat-8.0.24/bin/                                       
./shutdown.sh                                                                 
                                                                              
echo checking tomcat shutdown ==============                                  
tomcatstatus=1                                                                
cnt=0                                                                         
                                                                              
while [ ${cnt} -le 50 ] && [ ${tomcatstatus} -eq 1 ]                          
do                                                                            
    busyport=$(netstat -na | grep "LISTEN" | grep "31337" | wc -l)            
    if [ ${busyport} -eq 1 ]                                                  
    then                                                                      
        echo tomcat is working                                                
        sleep 1                                                               
    else                                                                      
        echo tomcat down                                                      
        tomcatstatus=0                                                        
    fi                                                                        
    cnt=`expr ${cnt} + 1 `                                                    
done                                                                          
                                                                              
echo war file copy =========================                                  
cd /home/kuku/apache-tomcat-8.0.24/webapps                                    
rm -rf ROOT.war                                                               
rm -rf ../ROOTBACKUP                                                          
mv ROOT ../ROOTBACKUP                                                         
mv /root/.jenkins/workspace/newITEM/target/triangle.war ./ROOT.war            
                                                                              
echo tomcat start ==========================                                  
cd /home/kuku/apache-tomcat-8.0.24/bin/                                       
sudo sh ./startup.sh     

echo checking tomcat start =================                                                                                       
cnt=0                                                                    
while [ ${cnt} -le 30 ] && [ ${tomcatstatus} -eq 0 ]                     
do                                                                       
        busyport=$(netstat -na | grep "LISTEN" | grep "31337" | wc -l)   
        if [ ${busyport} -eq 0 ]                                         
        then                                                             
                echo tomcat is down...                                   
                sleep 1                                                  
        else                                                             
                echo tomcat start                                        
                tomcatstatus=1                                           
        fi                                                               
        cnt=`expr ${cnt} + 1 `                                           
done                                                                     
                                                                         
echo checking deploy =======================                             
servicestatus=0                                                          
dirname="/home/kuku/apache-tomcat-8.0.24/webapps/ROOT"                   
                                                                         
while [ ${cnt} -le 30 ] && [ ${servicestatus} -eq 0 ]                    
do                                                                       
    if [ -d ${dirname} ]                                                 
    then                                                                 
        echo Deployment is complete.                                     
        servicestatus=1                                                  
    else                                                                 
        echo Deploying...                                                
        sleep 1                                                          
    fi                                                                   
    cnt=`expr ${cnt} + 1 `                                               
done                                                                     
                                                                         
echo chekpage swap =========================                             
cd /etc/nginx/sites-available/                                           
mv kuku checkkuku                                                        
mv originkuku kuku                                                       
                                                                         
echo nginx reload ==========================                             
service nginx reload                                                     