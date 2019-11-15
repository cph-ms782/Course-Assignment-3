Master  
[![Build Status](https://travis-ci.org/cph-ms782/Course-Assignment-3_Backend.svg?branch=master)](https://travis-ci.org/cph-ms782/Course-Assignment-3_Backend)  

Dev  
[![Build Status](https://travis-ci.org/cph-ms782/Course-Assignment-3_Backend.svg?branch=dev)](https://travis-ci.org/cph-ms782/Course-Assignment-3_Backend)  

# Course-Assignment-3  


### endpoints  

**All**  

 * /api/info  
 * /api/info/all  
 * /api/sw  
 * /api/login  

**User**  

 * /api/sw/data  
 * /api/info/user  

**Admin**  

 * /api/sw/data  
 * /api/info/user  
 * /api/info/admin  
 * /api/info/fill  

--------------------------------------------------------------
Nedenst�ende er en minimalistisk guide til ops�tning i forbindelse med deployment igennem travis.
--------------------------------------------------------------

1.  MySql
    opret lokal(xxx + xxx_test) og droplet(xxx) database.

2. Netbeans Backend Projekt. (Project Files -> pom.xml).
    kontroll�r pom.xml linie 18(remote.server).
    Husk at kontroll�r at der bruges https og ikke http.

3. Netbeans Backend Projekt. 
(Source packages -> utils -> EMF_Creator.java)
    s�rg for at connection i EMF_Creator linie 121 	"CONNECTION" er unik.

4. Droplet(Terminal/GitBash)
    ssh ind i dropletten, og brug
    - sudo nano /opt/tomcat/bin/setenv.sh -
    opret ny export med v�rdien fra "CONNECTION" i step 3.

5. Netbeans Backend Projekt
(Other sources -> src/main/resources -> default package -> config.properties)
    kontroll�r database navne i config.properties.

6. Travis.yml fil i rod mappen.
    �ndre database navn i linie 40(CREATE DATABASE ??) til database navnet valgt i step 1.

7. https://travis-ci.org/
    p� travis s�t REMOTE_USER til "script_user" og REMOTE_PW til script_user�s password.
    
    -- brug f�lgende kommando for at finde password til brugeren hvis n�dvendigt.
    sudo nano /opt/tomcat/conf/tomcat-users.xml



HUSK AT CLEAN AND BUILD HVIS DER ER ERRORS I IMPORTS.
