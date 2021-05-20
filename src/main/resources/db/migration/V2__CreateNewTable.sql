CREATE TABLE IF NOT EXISTS `student_flyway` (

                                                `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                `name` varchar(20) NOT NULL ,
                                                `email` varchar(50) NOT NULL ,
                                                `average` float NOT NULL ,
                                                `phone_number` varchar(10) NOT NULL

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;