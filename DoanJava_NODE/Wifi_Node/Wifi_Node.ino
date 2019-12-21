#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
#include <ArduinoJson.h>
#include "DHT.h"
#define DHTPIN 5     // Digital pin connected to the DHT sensor
#define DHTTYPE DHT22   // DHT 22  (AM2302), AM2321

#define Green   12
#define Rainbow 13
#define Blue    15
#define WifiLed 2

#define ON LOW
#define OFF HIGH

float h = 0;
float t = 0;

DynamicJsonBuffer jsonBuffer;
DHT dht(DHTPIN, DHTTYPE);
HTTPClient http; // Thủ tục tạo 1 object http

const char* ssid = "Xiu";           // ID wifi
const char* password = "123456789"; // Pass wifi
const char* host= "http://192.168.137.1:1234/Java/JavaReceiveData.php"; 

void setup() {

  pinMode(Green,OUTPUT);
  pinMode(Rainbow,OUTPUT);
  pinMode(Blue,OUTPUT);  
  pinMode(WifiLed,OUTPUT);

  digitalWrite(Rainbow,OFF);
  digitalWrite(Green,OFF);
  digitalWrite(Blue,OFF);

  Serial.begin(9600);   // Set baudrate UART để giao tiếp với HERCULES hoặc terminal của Arduino IDE
  WiFi.begin(ssid, password);   // Bắt đầu kết nối
  
  boolean toggleled = true;
  while (WiFi.status() != WL_CONNECTED) { // Hiển thị trạng thái đang kết nối
    delay(500);
    toggleled = !toggleled;
    digitalWrite(WifiLed,toggleled);
  }
  digitalWrite(WifiLed,OFF);
  dht.begin();
  delay(2000);
  
}
void loop() {

  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  h = dht.readHumidity() * 100;
  // Read temperature as Celsius (the default)
  t = dht.readTemperature() * 100;
  if (isnan(h) || isnan(t)) {
    Serial.println(F("Failed to read from DHT sensor!"));
    return;
  }

  http.begin(host);         // Bắt đầu với host bên trên
  http.addHeader("Content-Type", "application/x-www-form-urlencoded"); // Thủ tục 
  int httpCode=http.POST("Hum="+(String)h + "&Temp=" + (String)t); 
  String payload=http.getString(); 
  Serial.println(payload);   

  JsonArray &root = jsonBuffer.parseArray(payload); // Tách JSON ra
   if (!root.success()) {
    Serial.println("parseObject() failed");
    return;
  }
    
  digitalWrite(Green,root[2]);
  digitalWrite(Rainbow,root[1]);
  digitalWrite(Blue,root[0]);
 
  delay(50);
  http.end();            

  delay(5000);
  
}
