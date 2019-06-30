-- Creación de las categorías

-- Creación de las categorias

INSERT INTO `trivial`.`categorias` (`id_categoria`, `categoria`, `puntuacion_minima`) VALUES ('1', 'Ciencias', '0');
INSERT INTO `trivial`.`categorias` (`id_categoria`, `categoria`, `puntuacion_minima`) VALUES ('2', 'Deportes', '170');
INSERT INTO `trivial`.`categorias` (`id_categoria`, `categoria`, `puntuacion_minima`) VALUES ('3', 'Espectáculo', '380');
INSERT INTO `trivial`.`categorias` (`id_categoria`, `categoria`, `puntuacion_minima`) VALUES ('4', 'Historia', '570');
INSERT INTO `trivial`.`categorias` (`id_categoria`, `categoria`, `puntuacion_minima`) VALUES ('5', 'Geografía', '780');
INSERT INTO `trivial`.`categorias` (`id_categoria`, `categoria`, `puntuacion_minima`) VALUES ('6', 'Literatura', '970');

-- Creación de varías preguntas para cada una de las categorías

INSERT INTO `trivial`.`preguntas` (`id_pregunta`, `pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('1', '¿Qué animal aparece en la bandera de Rohan?', 'Caballo', 'Perro', 'Dragón', 'Conejo', 'Aguila', '3');
INSERT INTO `trivial`.`preguntas` (`id_pregunta`, `pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('2', 'Lugar donde se desarrolla la historia de \'Juego de Tronos\'', 'Poniente', 'Oriente', 'Turquía', 'Marruecos', 'Asgard', '3');
INSERT INTO `trivial`.`preguntas` (`id_pregunta`, `pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('3', '¿Cuál es el último Horrocrux que se destruye en las películas de Harry Potter?', 'Nagini', 'Diario', 'Guardapelo', 'Diadema', 'Copa', '3');
INSERT INTO `trivial`.`preguntas` (`id_pregunta`, `pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('4', '¿De qué deporte es el longboard una de las modalidades?', 'Skateboard', 'Snowboard', 'Aplinismo', 'Surf', 'Atletismo', '2');
INSERT INTO `trivial`.`preguntas` (`id_pregunta`, `pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('5', '¿En cuántos mundiales de fútbol participó la selección de Canadá?', '1', '4', '3', '0', '2', '2');
INSERT INTO `trivial`.`preguntas` (`pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('¿Cuántos minutos dura cada cuarto de baloncesto?', '10', '15', '20', '17', '25', '2');
INSERT INTO `trivial`.`preguntas` (`id_pregunta`, `pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('7', 'Al descendiente de un asno y una yegua se le conoce como:', 'Mulo', 'Asno', 'Burro', 'Caballo', 'Potro', '1');
INSERT INTO `trivial`.`preguntas` (`id_pregunta`, `pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('8', '¿Cómo se llama la ciencia que estudia los fósiles?', 'Paleontología', 'Geología', 'Biología', 'Sociología', 'Astrología', '1');
INSERT INTO `trivial`.`preguntas` (`id_pregunta`, `pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('9', 'Las especies de seres vivos de divien en reinos. ¿cuántos hay?', '5', '6', '4', '2', '3', '1');
INSERT INTO `trivial`.`preguntas` (`id_pregunta`, `pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('10', '¿Cuál de estos países africanos NO tiene costa?', 'Chad', 'Togo', 'Costa de Marfil', 'Namibia', 'Senegal', '5');
INSERT INTO `trivial`.`preguntas` (`id_pregunta`, `pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('11', '¿Qué periodo comprende la Segunda Guerra Mundial?', '1939-1945', '1936-1939', '1940-1942', '1939-1944', '1940-1946', '4');
INSERT INTO `trivial`.`preguntas` (`id_pregunta`, `pregunta`, `correcta`, `incorrecta1`, `incorrecta2`, `incorrecta3`, `incorrecta4`, `id_categoria`) VALUES ('12', '¿Quién escribió la \'Trilogía de la Niebla\'?', 'Carlos Ruiz Zafón', 'Ana Todd', 'Pérez-Reverte', 'Paulo Cohelo', 'Gabriel García Márquez', '6');

-- Creación de un usuario administrador y un usuario normal
INSERT INTO `trivial`.`usuarios` VALUES (1,'admin','RG5XQ9GFdMvh4p//uRc8Iw==');
INSERT INTO `trivial`.`usuarios` VALUES (2,'usuario','d7IC8a42ZWFNmdxaP8GZ0Q==');

-- Dar al usuario 'usuario' la puntuación necesaria para que pueda verlo todo
INSERT INTO `trivial`.`puntuacion` (`id_usuario`, `id_categoria`, `puntos`) VALUES ('2', '1', '200');
INSERT INTO `trivial`.`puntuacion` (`id_usuario`, `id_categoria`, `puntos`) VALUES ('2', '2', '200');
INSERT INTO `trivial`.`puntuacion` (`id_usuario`, `id_categoria`, `puntos`) VALUES ('2', '3', '200');
INSERT INTO `trivial`.`puntuacion` (`id_usuario`, `id_categoria`, `puntos`) VALUES ('2', '4', '200');
INSERT INTO `trivial`.`puntuacion` (`id_usuario`, `id_categoria`, `puntos`) VALUES ('2', '5', '200');
INSERT INTO `trivial`.`puntuacion` (`id_usuario`, `id_categoria`, `puntos`) VALUES ('2', '6', '200');
