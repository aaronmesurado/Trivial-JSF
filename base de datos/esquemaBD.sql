-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema trivial
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema trivial
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `trivial` DEFAULT CHARACTER SET latin1 ;
USE `trivial` ;

-- -----------------------------------------------------
-- Table `trivial`.`categorias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trivial`.`categorias` (
  `id_categoria` INT(11) NOT NULL AUTO_INCREMENT,
  `categoria` VARCHAR(45) NOT NULL,
  `puntuacion_minima` INT(11) NOT NULL,
  PRIMARY KEY (`id_categoria`),
  UNIQUE INDEX `categoria_UNIQUE` (`categoria` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `trivial`.`preguntas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trivial`.`preguntas` (
  `id_pregunta` INT(11) NOT NULL AUTO_INCREMENT,
  `pregunta` VARCHAR(100) NOT NULL,
  `correcta` VARCHAR(50) NOT NULL,
  `incorrecta1` VARCHAR(50) NOT NULL,
  `incorrecta2` VARCHAR(50) NOT NULL,
  `incorrecta3` VARCHAR(50) NOT NULL,
  `incorrecta4` VARCHAR(50) NOT NULL,
  `id_categoria` INT(11) NOT NULL,
  PRIMARY KEY (`id_pregunta`),
  UNIQUE INDEX `pregunta_UNIQUE` (`pregunta` ASC),
  INDEX `fk_preguntas_categorias1_idx` (`id_categoria` ASC),
  CONSTRAINT `fk_preguntas_categorias1`
    FOREIGN KEY (`id_categoria`)
    REFERENCES `trivial`.`categorias` (`id_categoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `trivial`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trivial`.`usuarios` (
  `id_usuario` INT(11) NOT NULL AUTO_INCREMENT,
  `usuario` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE INDEX `usuario_UNIQUE` (`usuario` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `trivial`.`puntuacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trivial`.`puntuacion` (
  `id_usuario` INT(11) NOT NULL,
  `id_categoria` INT(11) NOT NULL,
  `puntos` INT NULL,
  PRIMARY KEY (`id_usuario`, `id_categoria`),
  INDEX `fk_usuarios_has_categorias_categorias1_idx` (`id_categoria` ASC),
  INDEX `fk_usuarios_has_categorias_usuarios1_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_usuarios_has_categorias_usuarios1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `trivial`.`usuarios` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuarios_has_categorias_categorias1`
    FOREIGN KEY (`id_categoria`)
    REFERENCES `trivial`.`categorias` (`id_categoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
