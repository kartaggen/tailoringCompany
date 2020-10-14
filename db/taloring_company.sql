-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema tailoring_company
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tailoring_company
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tailoring_company` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `tailoring_company` ;

-- -----------------------------------------------------
-- Table `tailoring_company`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tailoring_company`.`company` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `profit_tax` DOUBLE NOT NULL,
  `salary_exp` DOUBLE NOT NULL,
  `salary_inexp` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tailoring_company`.`employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tailoring_company`.`employees` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(20) NOT NULL,
  `experienced` BIT(1) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tailoring_company`.`materials`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tailoring_company`.`materials` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `color` VARCHAR(20) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `price_per_sqm` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tailoring_company`.`parts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tailoring_company`.`parts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `exp_make_per_month` INT NOT NULL,
  `inexp_make_per_month` INT NOT NULL,
  `material_needed` DOUBLE NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `sell_price` DOUBLE NOT NULL,
  `material_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_parts_materials_idx` (`material_id` ASC) VISIBLE)
ENGINE = MyISAM
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tailoring_company`.`work_orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tailoring_company`.`work_orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `completion_date` DATE NOT NULL,
  `quantity` INT NOT NULL,
  `employee_id` BIGINT NOT NULL,
  `part_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_work_orders_employees1_idx` (`employee_id` ASC) VISIBLE,
  INDEX `fk_work_orders_parts1_idx` (`part_id` ASC) VISIBLE)
ENGINE = MyISAM
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO company (id,name, profit_tax, salary_exp, salary_inexp)
VALUES ('1','New Tailoring company','20.00','1600.00','1200.00');