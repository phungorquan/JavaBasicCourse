-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 11, 2020 at 11:58 AM
-- Server version: 10.1.36-MariaDB
-- PHP Version: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `doanjava`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `Id` bigint(10) NOT NULL,
  `UserName` varchar(20) COLLATE utf8_vietnamese_ci NOT NULL,
  `PassWord` varchar(20) COLLATE utf8_vietnamese_ci NOT NULL,
  `Function` varchar(20) COLLATE utf8_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`Id`, `UserName`, `PassWord`, `Function`) VALUES
(1, 'Admin', 'Admin', 'Admin'),
(2, 'User1', '2', 'User'),
(5, 'User3', '123', 'User');

-- --------------------------------------------------------

--
-- Table structure for table `devices`
--

CREATE TABLE `devices` (
  `Id` bigint(10) NOT NULL,
  `Name` varchar(20) COLLATE utf8_vietnamese_ci NOT NULL,
  `Status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `devices`
--

INSERT INTO `devices` (`Id`, `Name`, `Status`) VALUES
(1, 'Green', 0),
(2, 'Rainbow', 0),
(3, 'Blue', 0);

-- --------------------------------------------------------

--
-- Table structure for table `dht22`
--

CREATE TABLE `dht22` (
  `Id` bigint(10) NOT NULL,
  `Name` varchar(10) COLLATE utf8_vietnamese_ci NOT NULL,
  `Temp` float NOT NULL,
  `Humi` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `dht22`
--

INSERT INTO `dht22` (`Id`, `Name`, `Temp`, `Humi`) VALUES
(1, 'DHT22', 35.1, 67.5);

-- --------------------------------------------------------

--
-- Table structure for table `hisdht22`
--

CREATE TABLE `hisdht22` (
  `Id` bigint(20) NOT NULL,
  `MaxTemp` float NOT NULL,
  `MinTemp` float NOT NULL,
  `MaxHum` float NOT NULL,
  `MinHum` float NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `hisdht22`
--

INSERT INTO `hisdht22` (`Id`, `MaxTemp`, `MinTemp`, `MaxHum`, `MinHum`, `Date`) VALUES
(1, 33.7, 26, 116, 77, '2019-12-13'),
(2, 32.5, 27.4, 107, 80, '2019-12-14'),
(8, 34.5, 33.2, 77.2, 70.3, '2019-12-15'),
(9, 34, 30.9, 99.9, 59.9, '2019-12-17'),
(10, 34.2, 25, 99.9, 59.9, '2019-12-21'),
(12, 34.4, 33.4, 99.9, 66, '2020-01-05'),
(13, 28.9, 28.4, 78.4, 75.1, '2020-01-06'),
(14, 25.7, 23.9, 99.9, 78.2, '2020-01-07');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `devices`
--
ALTER TABLE `devices`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `dht22`
--
ALTER TABLE `dht22`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `hisdht22`
--
ALTER TABLE `hisdht22`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `Id` bigint(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `devices`
--
ALTER TABLE `devices`
  MODIFY `Id` bigint(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `dht22`
--
ALTER TABLE `dht22`
  MODIFY `Id` bigint(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `hisdht22`
--
ALTER TABLE `hisdht22`
  MODIFY `Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
