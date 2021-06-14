CREATE TABLE IF NOT EXISTS `teacher_flyway` (

                                                `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                `name` varchar(20)  NOT NULL ,
                                                `email` varchar(50)  ,
                                                `salary` float NOT NULL ,
                                                `degree` varchar(10) NOT NULL

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;