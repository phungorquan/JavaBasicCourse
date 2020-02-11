#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
#include <ArduinoJson.h>
#include "DHT.h"
#define DHTPIN 5     // Digital pin connected to the DHT sensor
#define DHTTYPE DHT22   // DHT 22  (AM2302), AM2321


#define Blue   12   // Blue led pin     // LED  3
#define Rainbow 13  // Rainbow led pin  // LED  2
#define Green    15 // Green led pin    // LED  1
#define WifiLed 2   // Default led on board

#define GreenCtrl 14  // Green button control pin
#define RainbowCtrl 0 // Rainbow button control pin
#define BlueCtrl 4    // Blue button control pin

float h = 0;          // Variable to save Hum value
float t = 0;          // Variable to save Temp value
String payload ="";   // Payload get from Webserver
boolean GreenTmpStatus;   // Temp Green led status
boolean RainbowTmpStatus; // Rainbow Green led status
boolean BlueTmpStatus;    // Blue Green led status

long previousMillis = 0;  
long interval = 5000;     // Timer 5s

DynamicJsonBuffer jsonBuffer;
DHT dht(DHTPIN, DHTTYPE);
HTTPClient http; 

const char* ssid = "Xiu";           // ID wifi
const char* password = "123456789"; // Pass wifi
const char* host= "http://192.168.137.1:1234/Java/JavaReceiveData.php"; 

void setup() {

  pinMode(Green,OUTPUT);    // Set Green led pin is OUTPUT
  pinMode(Rainbow,OUTPUT);  // Set Rainbow led pin is OUTPUT
  pinMode(Blue,OUTPUT);     // Set Blue led pin is OUTPUT
  pinMode(WifiLed,OUTPUT);  // Set Wifi led pin is OUTPUT

  digitalWrite(Green,LOW);    // Turn Green led off
  digitalWrite(Rainbow,LOW);  // Turn Rainbow off
  digitalWrite(Blue,LOW);     // Turn Blue led off

  pinMode(GreenCtrl,INPUT_PULLUP);  // Set Green button control is internal Pull up
  pinMode(RainbowCtrl,INPUT_PULLUP);  // Set Rainbow button control is internal Pull up
  pinMode(BlueCtrl,INPUT_PULLUP);   // Set Blue button control is internal Pull up

  Serial.begin(9600);   // Baudrate to commute with Arduino Serial Monitor
  WiFi.begin(ssid, password);   // Connect
  
  boolean toggleled = true; // Variable to toggle Wifiled
  while (WiFi.status() != WL_CONNECTED) { 
    delay(500); 
    toggleled = !toggleled; // Blink led until connect wifi
    digitalWrite(WifiLed,toggleled);
  }
  digitalWrite(WifiLed,HIGH);  // If connected -> turn Wifi led off
  dht.begin();  // Start read Hum and Temp
  delay(1000);
  
}
void loop() {
  unsigned long currentMillis = millis();
  if(currentMillis - previousMillis > interval) {
    previousMillis = currentMillis;
    
    h = dht.readHumidity() * 100; // Read Hum
    t = dht.readTemperature() * 100; // Read Temp
    // Check value or device is stable or not
    if (isnan(h) || isnan(t)) {
      Serial.println(F("Failed to read from DHT sensor!"));
      return;
    }

  http.begin(host);         // Connect to Webserver
  http.addHeader("Content-Type", "application/x-www-form-urlencoded");  
  http.POST("Hum="+(String)h + "&Temp=" + (String)t + "&Getstt=x"); // Post to Webserver with 3 field, Hum Temp and Getstt
  payload=http.getString();  // Receive echo from Webserver (Ledstt)
  Serial.println(payload);   // Print to Serial Monitor
  
  JsonArray &root = jsonBuffer.parseArray(payload); // Decompose JSON
   if (!root.success()) {
    Serial.println("parseObject() failed");
    return;
  }
    
  digitalWrite(Green,root[0]);    // Turn on or off Green led as JSON
  digitalWrite(Rainbow,root[1]);  // Turn on or off Green led as JSON
  digitalWrite(Blue,root[2]);     // Turn on or off Green led as JSON
  GreenTmpStatus = root[0];       // Save the status out to sync status
  RainbowTmpStatus = root[1];     // Save the status out to sync status
  BlueTmpStatus = root[2];        // Save the status out to sync status
  
    delay(50);
  http.end();                     // Close connection
  }

  if(digitalRead(GreenCtrl) == 0) // If press Green control button
  {
      boolean Status = !GreenTmpStatus; // Get NOT status
      digitalWrite(Green,Status);       // Turn NOT status
      while(GreenTmpStatus != Status)   // Sync until status on Webserver == status of device
      {
        http.POST("Led=1" + (String)Status);  // Post status of device has just changed
        delay(1000);          
        http.POST("Getstt=x");          // Get status from Webserver 
        payload=http.getString();       // Save to payload
        Serial.println(payload);        // Print to Serial Monitor
        JsonArray &root = jsonBuffer.parseArray(payload);   // Decompose JSON
        GreenTmpStatus = root[0];       // Save to this to compare
        delay(1000);
      }
    }

  else if(digitalRead(RainbowCtrl) == 0)
  {
     boolean Status = !RainbowTmpStatus;
      digitalWrite(Rainbow,Status);
      while(RainbowTmpStatus != Status)
      {
        http.POST("Led=2" + (String)Status);
        delay(1000);
        http.POST("Getstt=x"); 
        payload=http.getString(); 
        Serial.println(payload);  
        JsonArray &root = jsonBuffer.parseArray(payload); 
        RainbowTmpStatus = root[1];
        delay(1000);
      }
    }

  else if(digitalRead(BlueCtrl) == 0)
  {
    boolean Status = !BlueTmpStatus;
      digitalWrite(Blue,Status);
      while(BlueTmpStatus != Status)
      {
        http.POST("Led=3" + (String)Status);
        delay(1000);
        http.POST("Getstt=x"); 
        payload=http.getString(); 
        Serial.println(payload);  
        JsonArray &root = jsonBuffer.parseArray(payload); 
        BlueTmpStatus = root[2];
        delay(1000);
      }
    }

}
