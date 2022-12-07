-- MySQL Script generated by MySQL Workbench
-- Wed Dec  7 20:42:10 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gaucha_urn
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `gaucha_urn` ;

-- -----------------------------------------------------
-- Schema gaucha_urn
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gaucha_urn` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `gaucha_urn` ;

-- -----------------------------------------------------
-- Table `gaucha_urn`.`candidates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gaucha_urn`.`candidates` (
  `candidate_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` TINYTEXT NOT NULL,
  `last_name` TINYTEXT NOT NULL,
  `date_birth` DATE NULL,
  PRIMARY KEY (`candidate_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gaucha_urn`.`candidate_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gaucha_urn`.`candidate_types` (
  `id_ct` INT NOT NULL AUTO_INCREMENT,
  `name` TINYTEXT NOT NULL,
  `notes` MEDIUMTEXT NULL,
  PRIMARY KEY (`id_ct`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gaucha_urn`.`voters`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gaucha_urn`.`voters` (
  `id_voter` INT NOT NULL AUTO_INCREMENT,
  `first_name` TINYTEXT NULL,
  `last_name` TINYTEXT NULL,
  `date_birth` DATE NULL,
  PRIMARY KEY (`id_voter`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gaucha_urn`.`turn`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gaucha_urn`.`turn` (
  `id_turn` INT NOT NULL AUTO_INCREMENT,
  `dt_begin` DATETIME NULL,
  `dt_end` DATETIME NULL,
  PRIMARY KEY (`id_turn`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gaucha_urn`.`vote_reg`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gaucha_urn`.`vote_reg` (
  `id_vote` INT NOT NULL AUTO_INCREMENT,
  `dt_vote` DATETIME NULL,
  `voted_candidate` INT NOT NULL,
  `voter_id` INT NOT NULL,
  `turn_id` INT NOT NULL,
  `candidate_to` INT NOT NULL,
  PRIMARY KEY (`id_vote`, `voted_candidate`, `voter_id`, `turn_id`, `candidate_to`),
  INDEX `fk_vote_reg_candidates1_idx` (`voted_candidate` ASC) VISIBLE,
  INDEX `fk_vote_reg_voters1_idx` (`voter_id` ASC) VISIBLE,
  INDEX `fk_vote_reg_turn1_idx` (`turn_id` ASC) VISIBLE,
  INDEX `fk_vote_reg_candidate_types1_idx` (`candidate_to` ASC) VISIBLE,
  CONSTRAINT `fk_vote_reg_candidates1`
    FOREIGN KEY (`voted_candidate`)
    REFERENCES `gaucha_urn`.`candidates` (`candidate_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vote_reg_voters1`
    FOREIGN KEY (`voter_id`)
    REFERENCES `gaucha_urn`.`voters` (`id_voter`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vote_reg_turn1`
    FOREIGN KEY (`turn_id`)
    REFERENCES `gaucha_urn`.`turn` (`id_turn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vote_reg_candidate_types1`
    FOREIGN KEY (`candidate_to`)
    REFERENCES `gaucha_urn`.`candidate_types` (`id_ct`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;