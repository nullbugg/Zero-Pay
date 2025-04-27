-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 11, 2025 at 12:36 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Zero_Pay`
--

-- --------------------------------------------------------

--
-- Table structure for table `Accounts_Balance`
--

CREATE TABLE `Accounts_Balance` (
  `Phone` varchar(11) DEFAULT NULL,
  `Current_Balance` double DEFAULT NULL,
  `Transaction` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Accounts_Balance`
--

INSERT INTO `Accounts_Balance` (`Phone`, `Current_Balance`, `Transaction`) VALUES
('01912345678', 907, '23.0 TK Withdraw'),
('01787654321', 870, '20 TK Received From 01912345678'),
('01712345678', 50, '50 TK Send to 01912345678');

-- --------------------------------------------------------

--
-- Table structure for table `Admin_Info`
--

CREATE TABLE `Admin_Info` (
  `Username` varchar(32) NOT NULL,
  `Password` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Admin_Info`
--

INSERT INTO `Admin_Info` (`Username`, `Password`) VALUES
('a', 'a'),
('alim', 'admin'),
('arif', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `Branch`
--

CREATE TABLE `Branch` (
  `Branch_ID` int(11) NOT NULL,
  `Branch_Name` varchar(255) DEFAULT NULL,
  `Branch_Address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Branch`
--

INSERT INTO `Branch` (`Branch_ID`, `Branch_Name`, `Branch_Address`) VALUES
(101, 'Branch_A', 'Dhaka'),
(201, 'Branch_B', 'Bogura'),
(301, 'Branch_C', 'Uttora'),
(401, 'D', 'Changao');

-- --------------------------------------------------------

--
-- Table structure for table `Customer_Info`
--

CREATE TABLE `Customer_Info` (
  `Phone` varchar(11) NOT NULL,
  `Full_Name` varchar(32) DEFAULT NULL,
  `Email` varchar(32) DEFAULT NULL,
  `Password` varchar(32) NOT NULL,
  `Branch_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Customer_Info`
--

INSERT INTO `Customer_Info` (`Phone`, `Full_Name`, `Email`, `Password`, `Branch_ID`) VALUES
('01712345678', 'Ariful Islam', 'arif@gmail.com', '1122', 201),
('01787654321', 'Husain', 'husain@gmail.com', '1111', 101),
('01912345678', 'Abdul Alim', 'alim@gmail.com', '1234', 101);

-- --------------------------------------------------------

--
-- Table structure for table `Employee`
--

CREATE TABLE `Employee` (
  `Employee_ID` int(11) NOT NULL,
  `Employee_Name` varchar(32) DEFAULT NULL,
  `Phone` varchar(11) DEFAULT NULL,
  `Salary` decimal(10,2) DEFAULT NULL,
  `Branch_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Employee`
--

INSERT INTO `Employee` (`Employee_ID`, `Employee_Name`, `Phone`, `Salary`, `Branch_ID`) VALUES
(1001, 'Abdul Alim', '01987654321', 10000.00, 101),
(1002, 'Anando', '01234567890', 103900.00, 201),
(1003, 'Leon', '01234569824', 34500.00, 101),
(1004, 'Alif', '01987646859', 30000.00, 201),
(1005, 'Masum', '654565343', 20000.00, 201),
(1006, 'Husain', '01798745601', 25000.00, 301),
(1007, 'Akib', '01912343214', 90000.00, 301),
(1008, 'Arif', '01509123487', 50000.00, 301),
(1009, 'Ramzan', '01387612309', 60000.00, 201);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Accounts_Balance`
--
ALTER TABLE `Accounts_Balance`
  ADD KEY `accounts_balance_ibfk_1` (`Phone`);

--
-- Indexes for table `Admin_Info`
--
ALTER TABLE `Admin_Info`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `Branch`
--
ALTER TABLE `Branch`
  ADD PRIMARY KEY (`Branch_ID`);

--
-- Indexes for table `Customer_Info`
--
ALTER TABLE `Customer_Info`
  ADD PRIMARY KEY (`Phone`) USING BTREE,
  ADD KEY `customer_info_ibfk_1` (`Branch_ID`);

--
-- Indexes for table `Employee`
--
ALTER TABLE `Employee`
  ADD PRIMARY KEY (`Employee_ID`),
  ADD KEY `employee_ibfk_1` (`Branch_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Employee`
--
ALTER TABLE `Employee`
  MODIFY `Employee_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1010;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Accounts_Balance`
--
ALTER TABLE `Accounts_Balance`
  ADD CONSTRAINT `accounts_balance_ibfk_1` FOREIGN KEY (`Phone`) REFERENCES `Customer_Info` (`Phone`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Customer_Info`
--
ALTER TABLE `Customer_Info`
  ADD CONSTRAINT `customer_info_ibfk_1` FOREIGN KEY (`Branch_ID`) REFERENCES `Branch` (`Branch_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Employee`
--
ALTER TABLE `Employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`Branch_ID`) REFERENCES `Branch` (`Branch_ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
