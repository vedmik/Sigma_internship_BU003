
# This is a skeleton project

A brief description of what this project does and who it's for
___
## Tech Stack

**Server:** Spring boot, Lombok

**Language:** Java:17

**Builder:** Gradle 
___
## Run Locally

install java 17

Clone the project

```bash
  git clone https://github.com/vedmik/Sigma_internship_BU003.git
```

Go to the project directory and Start the server

```bash
  ./gradlew bootRun
```
You can use the `Ctrl + C` to stop the Spring Boot application.
___
## Run on AWS with DockerHUB (actual in November 2022)

This method will allow you to use the app with a public IP 

### **1. Start or Create AWS instance**<br>
🛠 **_If you have your Linux instance or know how to run it on AWS, go to point 2._**

- **1.1 Open ES2 Dashboard on AWS**
  <br><br>
- **1.2 Press button `Launch instance`**.
  <br><br>
- **1.3 Fill the fields** <br> recommendations for filling in data:
  - **1.3.1. Name and tags** `YOUR_VALUE`<br> Enter the instance name

  - **1.3.2. Application and OS Images (Amazon Machine Image)** `Amazon Linux 2 AMI(HVM)`
<br> Select an image with the comment **free level available**. 
If you want to use the image for free. You will have 750 hours of free usage.

  - **1.3.3. Instance type** `t2.micro` <br>Select an instance with the comment
**free level available**. If you want to use the image for free. You will have 750 hours of free usage.

  - **1.3.4. Key pair** `CHOOSE_YOUR_KEY_PAIR` <br>Create a new key pair if you don't already have one.

  - **1.3.5. Network settings** `Create new group` <br>At this point, I recommend allowing all traffic 
  to the instance, so select all the checkboxes.
    <br><br>
- **1.4. Press Launch instance** <br>After press button your instance is started.<br><br>
- **1.5. Connect to instance** <br> Go to `Connect to instance`. <br>On the tab `ES2 instance Connect` you can see 
Public IP address to your instance.<br> On the tab `SSH client` in the item "Example" is the data for connecting
to your instance. <br>In my case it's: <br> 
```bash
ssh -i "HP-andrii.vedmid.sigma.software.pem" ec2-user@ec2-3-123-154-112.eu-central-1.compute.amazonaws.com
```
<br><br>Open the console, select the folder where your SSH key is located _(from point 1.3.4.)_  and enter this command.


### 2. Install Docker and start app 

- **2.0 Update all package**
<br> If you create new instance on AWS run this command:
<br>

```
sudo yum update -y
```

- **2.1 Install Docker**<br>

```
sudo yum install docker -y
```


- **2.2 Start Docker**<br>

```
sudo service docker start
```


- **2.3 Start the APP**


```
sudo docker run -p 80:8080 vedmik/sigma.software.bu003.internship
```


- **2.4 Your app is running, Congratulations!!!** <br> Now your app is running and has the Public IP address we got in 1.5

___
## Author

- [@Andrii Vedmid](https://www.github.com/vedmik)

