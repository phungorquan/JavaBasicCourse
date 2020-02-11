<?php

$server   = "localhost:3306"; // Change this to correspond with your database port
$username = "root";
$password = "";
$DB       = "doanjava";


$conn = new mysqli($server, $username, $password, $DB);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// If NODEMCU send field "Hum" and "Temp"
if (isset($_POST["Hum"]) && isset($_POST["Temp"])) {
    $Hum  = $_POST["Hum"] / 100;	// Convert from DEC -> REAL from "Hum" field
    $Temp = $_POST["Temp"] / 100;	// Convert from DEC -> REAL	from "Temp" field
    
    $query = "UPDATE dht22 SET Temp = '$Temp',Humi = '$Hum' WHERE Id = 1";	// Update value of DHT22 into database
    $conn->query($query);	// Update
    
    $query    = "SELECT MaxTemp,MinTemp,MaxHum,MinHum,Date FROM hisdht22";	// Select all value from database to display into table of Java Application
    $result   = $conn->query($query);	// Select
    $row      = mysqli_fetch_all($result);	// Get all columns
    $lastdate = $row[sizeof($row) - 1][4];	// Get last row to compare to update new value in a day, make sure one row one day
    $GetToday = date("Y-m-d");				// Get today
    
    if ($GetToday > $lastdate) {	// Compare
        $query  = "INSERT INTO hisdht22 (Id,MaxTemp,MinTemp,MaxHum,MinHum,Date) VALUES ('','$Temp','$Temp','$Hum','$Hum','$GetToday')";
        $result = $conn->query($query);
    } else {
        $LastMaxTemp = $row[sizeof($row) - 1][0];
        $LastMinTemp = $row[sizeof($row) - 1][1];
        $LastMaxHum  = $row[sizeof($row) - 1][2];
        $LastMinHum  = $row[sizeof($row) - 1][3];
        
        // Check max min
        if ($LastMaxTemp != 0 && $LastMinTemp != 0 && $LastMaxHum != 0 && $LastMinHum != 0) {
            if ($LastMaxTemp <= $Temp)
                $LastMaxTemp = $Temp;
            else if ($LastMinTemp >= $Temp)
                $LastMinTemp = $Temp;
            
            if ($LastMaxHum <= $Hum)
                $LastMaxHum = $Hum;
            else if ($LastMinHum >= $Hum)
                $LastMinHum = $Hum;
            
            // Update max min if match into database
            $query = "UPDATE hisdht22 SET MaxTemp = '$LastMaxTemp', MinTemp = '$LastMinTemp' ,
        MaxHum  = '$LastMaxHum' , MinHum  = '$LastMinHum' WHERE Date = '$GetToday'";
            // Update
            $result = $conn->query($query);
        }
    }
}

// If NODEMCU send field "Led"
if (isset($_POST["Led"])) {
    $LedCommand = $_POST["Led"];	// Get data, the data include ID and Value (Eg: xy - x[0:2] , y[0:1])
    $ID         = substr("$LedCommand", 0, 1); // Get ID 
    $Stt        = substr("$LedCommand", 1, 1); // Get stt
    $query      = "UPDATE devices SET Status = '$Stt' WHERE Id = '$ID'"; // Update into database
    $result     = $conn->query($query);	// Update  
    $conn->close();	// Close database connection
    return;
}

// If NODEMCU send field "Getstt"
if (isset($_POST["Getstt"])) {
	// Connect to database
    $conn = new mysqli($server, $username, $password, $DB);
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $query = "SELECT Status from devices"; // Get LED stt
    $result = $conn->query($query);	// Get
    $parent = array(); // Create an array to save value
    while ($row = $result->fetch_assoc()) {
        array_push($parent, $row["Status"]); //Get one by one stt and save to array
    }
    echo json_encode($parent); // Echo as JSON array (send to NODEMCU)
    
    
}

$conn->close(); // Close database connection
?>