-- Consultas Sistema de Domotica

-- Configurar la llave primaria-autoincrementable
select setval ('persona_per_id_seq', 4);
select setval ('usuario_administrador_usu_id_seq', 1);
delete from persona where per_id in (4, 5, 6, 7, 8, 9, 10, 11, 12);
select *  from persona
delete from usuario_administrador where per_id in (12);
select setval ('usuario_administrador_usu_id_seq', 3);
select *  from usuario_administrador;

select *  from usuarios_login;

------------- USUARIOS

-- perfiles
SELECT p.perf_id, p.perf_nombre, p.perf_descripcion FROM perfil p;

SELECT p.perf_id, p.perf_nombre, p.perf_descripcion FROM perfil p WHERE p.perf_i;

SELECT p.perf_id, p.perf_nombre, p.perf_descripcion FROM perfil p
INNER JOIN perfil_usuario pu ON pu.perfu_perfil = p.perf_id 
INNER JOIN usuario u ON u.usu_id = pu.perfu_usuario 
WHERE u.usu_login = 'integrante1';

UPDATE perfil_usuario SET perfu_perfil = 2 
WHERE perfu_usuario = 9;

SELECT p.perf_id, p.perf_nombre, p.perf_descripcion FROM perfil p
INNER JOIN perfil_usuario pu ON pu.perfu_perfil = p.perf_id 
INNER JOIN usuario u ON u.usu_id = pu.perfu_usuario 
WHERE u.usu_login = 'integrante1';

select * from perfil_usuario;


---------------------EVENTOS

DELETE FROM evento;

SELECT eve_id, eve_fecha_hora, eve_tipo_evento, eve_mensaje, eve_confirmado FROM evento evt WHERE evt.eve_id = 513;

SELECT eve_id, eve_fecha_hora, eve_tipo_evento, eve_mensaje, eve_confirmado FROM evento evt WHERE evt.eve_id = 514;

SELECT COUNT(*) as cant FROM evento evt 
WHERE evt.eve_tipo_evento = ? AND evt.eve_mensaje = 'DEBERIAS ABRIR LA LLAVE, PORQUE ESTA NECESITANDO AGUA' AND evt.eve_confirmado = 0;




--------------- MEDICIONES

--- count mediciones
SELECT count(*) FROM medicion;

DELETE FROM medicion;

select *  from medicion;

SELECT *  FROM medicion WHERE med_dispositivo_item = 1 AND med_tipo_valor = 3;
-- ULTIMA MEDICION DEL SENSOR
SELECT med_valor FROM (SELECT med_id, med_dispositivo_item, med_tipo_valor, med_valor, med_fecha_hora, MAX(med_fecha_hora) OVER (PARTITION BY med_dispositivo_item, med_tipo_valor) as MaxDataDate
FROM medicion) x 
WHERE med_fecha_hora = MaxDataDate AND med_dispositivo_item = 1 AND med_tipo_valor = 3;



select *  from tipo_valor;


-------------- TIPO EVENTO

SELECT tie.tie_id, tie.tie_nombre, tie.tie_descripcion FROM tipo_evento tie WHERE tie.tie_id = 1;
DELETE FROM evento;
select *  from evento;


SELECT COUNT(*) as cant FROM evento evt 
WHERE evt.eve_tipo_evento = 2 AND evt.eve_mensaje = 'DEBERIAS ABRIR LA LLAVE, PORQUE ESTA NECESITANDO AGUA' AND evt.eve_confirmado = 0;


---------------  DISPOSITIVOS ITEM

-- BUSCAR POR ID
SELECT COUNT(*) as cant FROM dispositivo_item di WHERE di.di_id = 20;

-- OBTENER POR ID
SELECT di.di_id, di.di_codigo, di.di_dispositivo, di.di_nodo, di.di_estado FROM dispositivo_item di WHERE di.di_id = 1;

SELECT * FROM unidad;
SELECT * FROM tipo_valor;
SELECT * FROM actuador_valor;

-- LISTA
SELECT di.di_id, di.di_codigo, di.di_dispositivo, di.di_nodo, di.di_estado FROM dispositivo_item di

select *  from dispositivo_item;

-- MODIFICAR DISPOSITIVO ITEM
UPDATE dispositivo_item SET di_codigo = ?, di_dispositivo = ?, afi_paciente = ?, afi_eps = ?, afi_estado = ? WHERE afi_id = ?;
SELECT * FROM dispositivo_item;

-- MODIFICAR DISPOSITIVO ITEM ACTUADOR (VALOR)
UPDATE dispositivo_item_valor div SET div_valor = 1
WHERE div.div_tipo_valor = 7 AND div.div_dispositivo_item = 4 AND div.div_id = 4;


SELECT * FROM dispositivo_item_valor;    
SELECT * FROM dispositivo_item;
select *  from estado;

-- BUSCANDO EXISTENCIA DISPOSITIVO ITEM 
SELECT COUNT(*) as cant FROM dispositivo_item di WHERE di.di_id = 1 AND di.di_codigo = '199827-D' AND di.di_dispositivo = 1 AND di.di_nodo = 1  AND di.di_estado IN (1, 2);

-- ACTUADORES
SELECT di.di_id, di.di_codigo, di.di_dispositivo, di.di_nodo, di.di_estado FROM dispositivo_item di, dispositivo d, tipo_dispositivo td 
WHERE d.disp_id = di.di_dispositivo AND td.td_id = d.disp_tipo_dispositivo AND td.td_nombre = 'SENSOR'; 

SELECT di.di_id, di.di_codigo, di.di_dispositivo, di.di_nodo, di.di_estado FROM dispositivo_item di, dispositivo d, tipo_dispositivo td, nodo n, espacio e 
WHERE d.disp_id = di.di_dispositivo AND td.td_id = d.disp_tipo_dispositivo AND di.di_nodo = n.nod_id AND n.nod_espacio = e.esp_id 
AND td.td_nombre = 'SENSOR' AND e.esp_id = 9; 

-- OBTENER VALORES DE ACTUADOR

SELECT div.div_id, div.div_tipo_valor, div.div_valor 
FROM dispositivo_item di, dispositivo_item_valor div, tipo_valor tv, unidad u 
WHERE di.di_id = div.div_dispositivo_item AND tv.tiv_id = div.div_tipo_valor AND u.uni_id = tv.tiv_unidad AND  di.di_id = 4;


SELECT div.div_id, div.div_tipo_valor, div.div_valor FROM dispositivo_item_valor div WHERE div.div_id = 2;

-- TIPO VALOR
SELECT tv.tiv_id, tv.tiv_nombre, tv.tiv_rango_inicio, tv.tiv_rango_fin, tv.tiv_unidad FROM tipo_valor tv WHERE tv.tiv_id = 1;

-- OBTENER UNIDAD 
SELECT u.uni_id, u.uni_nombre, u.uni_acronimo, u.uni_descripcion FROM unidad u WHERE u.uni_id = 1;

-------------- NODO

-- BUSCAR POR ID

-- OBTENER POR ID
SELECT di.di_id, di.di_codigo, di.di_dispositivo, di.di_nodo, di.di_estado FROM dispositivo_item di WHERE di.di_id = ?;


-------------- TIPO NODO

-- BUSCAR POR ID

-- OBTENER POR ID
SELECT tin.tin_id, tin.tin_nombre, tin.tin_descripcion FROM tipo_nodo tin WHERE tin.tin_id = 1;



-------------- ESPACIO

-- BUSCAR POR ID

-- OBTENER POR ID
SELECT e.esp_id, e.esp_nombre, e.esp_descripcion, e.esp_estado, e.esp_tipo_espacio, e.esp_ancho, e.esp_largo, e.esp_vivienda FROM espacio e WHERE e.esp_id = 2;


-------------- TIPO ESPACIO

-- BUSCAR POR ID

-- BUSCAR POR ID
SELECT te.te_id, te.te_nombre, te.te_descripcion FROM tipo_espacio te WHERE te.te_id = 1;

---------- VIVIENDA

---- OBTENER POR ID
SELECT *  FROM vivienda;

SELECT v.viv_id, v.viv_direccion, v.viv_coordenada, v.viv_barrio, v.viv_ciudad, v.viv_estado, d.dep_id, p.pai_id 
FROM vivienda v, cuidad c, departamento d, pais p WHERE v.viv_ciudad = c.ciu_id AND d.dep_id = c.ciu_departamento AND p.pai_id = d.dep_pais AND v.viv_id = 1;

-- BUSCAR POR ID
SELECT COUNT(*) as cant FROM vivienda v WHERE v.viv_id = 14;


-- INSERTAR VIVIENDA
INSERT INTO vivienda (viv_codigo, viv_direccion, viv_coordenada, viv_barrio, viv_ciudad, viv_estado) VALUES ('655444-V', 'CRA44', 12, 'Manzanares', 1, 1);
select *  from vivienda;

--MODIFICAR
UPDATE vivienda v SET viv_codigo = ?, viv_direccion = ?, viv_coordenada = ?, viv_barrio = ?, viv_ciudad = ?, viv_estado = ? WHERE v.viv_id = ?;
UPDATE vivienda v SET viv_codigo = '22222-V', viv_direccion = 'Cra22', viv_coordenada = 12, viv_barrio = 'Manzanares 4', viv_ciudad = 1, viv_estado = 1 WHERE v.viv_id = 2;
SELECT * FROM estado;

SELECT v.viv_id, v.viv_codigo, v.viv_direccion, v.viv_coordenada, v.viv_barrio, v.viv_ciudad, v.viv_estado, d.dep_id, p.pai_id 
FROM vivienda v, cuidad c, departamento d, pais p WHERE v.viv_ciudad = c.ciu_id AND d.dep_id = c.ciu_departamento AND p.pai_id = d.dep_pais AND v.viv_id = ?;"

---------- COORDENADAS

-- INSERTAR
INSERT INTO coordenada (coo_latitud, coo_longitud, coo_altitud) VALUES ('2.623316', '-73.445574', '0');
SELECT *  FROM coordenada;


---------- DIPOSITIVO

--

-- OBTENER POR ID
SELECT d.disp_id, d.disp_nombre, d.disp_tipo_dispositivo, d.disp_estado, d.disp_categoria FROM dispositivo d WHERE d.disp_id = 1;


----------- CATEGORIA


--- OBTENER POR ID
SELECT c.cat_id, c.cat_nombre, c.cat_descripcion FROM categoria c WHERE c.cat_id = 1;
select * from categoria;


----------- TIPO DISPOSITIVO

--

-- OBTENER POR ID
SELECT td.td_id, td.td_nombre, td.td_descripcion FROM tipo_dispositivo td WHERE td.td_id = 1;







-- Vista para los usuarios -----------YA NO SE NECESITA
CREATE OR REPLACE VIEW usuarios_login AS 
 SELECT ua.usu_persona,
    ua.usu_login,
    ua.usu_password,
    ua.usu_perfil
   FROM usuario_administrador ua
     JOIN persona p ON ua.usu_persona = p.per_id
  WHERE ua.usu_estado = 1
UNION
 SELECT up.usu_persona,
    up.usu_login,
    up.usu_password,
    up.usu_perfil
   FROM usuario_paciente up
     JOIN persona p ON up.usu_persona = p.per_id
  WHERE up.usu_estado = 1
UNION
 SELECT um.usu_persona,
    um.usu_login,
    um.usu_password,
    um.usu_perfil
   FROM usuario_medico um
     JOIN persona p ON um.usu_persona = p.per_id
  WHERE um.usu_estado = 1
UNION
 SELECT uf.usu_persona,
    uf.usu_login,
    uf.usu_password,
    uf.usu_perfil
   FROM usuario_familiar uf
     JOIN persona p ON uf.usu_persona = p.per_id
  WHERE uf.usu_estado = 1;

ALTER TABLE usuarios_login
  OWNER TO postgres;

  -- Listar los estados
  select e.est_id, e.est_nombre, e.est_descripcion from estado e;

-- Listar generos
SELECT g.gen_id, g.gen_nombre, g.gen_acronimo FROM genero g

  -- Lista de tipos de identificaicon
  SELECT ti.tii_id, ti.tii_nombre, ti.tii_acronimo  FROM tipo_identificacion ti;

select *  from tipo_identificacion;
-- Lista de paises
select pai_id, pai_nombre from pais order by pai_nombre;

-- Lista departamentos por pais
select dep_id, dep_nombre, dep_pais from departamento where dep_pais = 1 order by dep_nombre

-- Busca pais por id
select pai_id, pai_nombre from pais where pai_id = 1;

-- Busca departamento de la ciudad
select dep_id, dep_nombre from departamento where dep_id = 1;

-- Ciudades por Departamento
select ciu_id, ciu_nombre, ciu_departamento from cuidad where ciu_departamento = 1 order by ciu_nombre

-- Listar perfiles
SELECT DISTINCT usu_perfil FROM usuarios_login;

--- Tipos de Organizacion
SELECT tor.to_id, tor.to_nombre, tor.to_descripcion FROM tipo_organizacion tor

--  tipos de contrato
SELECT tic.tic_id, tic.tic_nombre, tic.tic_descripcion FROM tipo_contrato tic;

-- seleccionar 
SELECT ep.eps_id, ep.eps_nombre, ep.eps_rut, ep.eps_tipo_organizacion, ep.eps_estado FROM eps ep WHERE ep.eps_id = 1;

-- Obtener tipo organizacion de la eps 
SELECT tor.to_id, tor.to_nombre, tor.to_descripcion FROM tipo_organizacion tor WHERE tor.to_id = 1;


--- EPS
SELECT eps_id, eps_nombre, eps_rut, eps_tipo_organizacion, eps_estado 
from (select row_number() over(order by ep.eps_nombre asc) AS RowNumber, 
ep.eps_id, ep.eps_nombre, ep.eps_rut, ep.eps_tipo_organizacion, ep.eps_estado FROM eps ep 
WHERE ep.eps_estado IN (1, 2) 
AND (ep.eps_nombre LIKE 'Cafesalud' OR ep.eps_rut LIKE '' OR ep.eps_tipo_organizacion = 0 OR ep.eps_estado = 0 )) 
as tabla where tabla.RowNumber between 1 and 2




--- Buscar usuario por email
SELECT COUNT(*) as cant FROM persona p 
INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE p.per_email = 'maria@gmail.com';  

select * from persona;

--Buscar usuario por identificacion
SELECT COUNT(*) as cant FROM persona p 
INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE p.per_identificacion = '1075284097';

-- Buscar el usuario por username
SELECT COUNT(*) as cant FROM persona p 
INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = 'luis';

-- Buscar usuario por id
SELECT COUNT(*) as cant FROM persona p 
INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_id = 1;

-- Busca sede por id
SELECT COUNT(*) as cant FROM eps ep WHERE ep.eps_id = 1;

-- Buscar contrato por id
SELECT con.con_id, con.con_codigo, con.con_tipo_contrato, con.con_estado FROM contrato con WHERE con.con_id = 1;
select *  from contrato;

-- Obtener tipo contrato por id
SELECT tic.tic_id, tic.tic_nombre, tic.tic_descripcion FROM tipo_contrato tic WHERE tic.tic_id = 2;


-- tipo identificacion del usuario
SELECT ti.tii_id, ti.tii_nombre, ti.tii_acronimo FROM tipo_identificacion ti 
INNER JOIN persona p ON p.per_tipo_identificacion = ti.tii_id 
INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = 'valen';


-- Obtener estado de la persona
SELECT e.est_id, e.est_nombre FROM estado e 
INNER JOIN persona p ON p.per_estado = e.est_id 
INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = 'luis';

-- Obtener genero del usuario
SELECT g.gen_id, g.gen_nombre, g.gen_acronimo FROM genero g 
INNER JOIN persona p ON p.per_genero = g.gen_id 
INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = 'luis';

-- Obtener roles del usuario
SELECT r.rol_id, r.rol_nombre FROM rol r 
INNER JOIN rol_usuario ru ON ru.rolusu_rol = r.rol_id 
INNER JOIN usuario u ON u.usu_id = ru.rolusu_usuario 
WHERE u.usu_login = 'fabio';





SELECT u.usu_perfil FROM usuario u WHERE u.usu_login = 'fabio';



-- Obtener informacion del usuario
SELECT p.per_id, p.per_identificacion, p.per_nombres, p.per_apellidos, p.per_email, p.per_fecha_nacimiento, p.per_telefono, p.per_direccion, u.usu_login, u.usu_password 
FROM persona p INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = 'luis';

-- Obtener Ciudad del usuario
select c.ciu_id, c.ciu_nombre, c.ciu_departamento from cuidad c 
INNER JOIN persona p ON p.per_ciudad = c.ciu_id 
INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = 'luis';


--obtener Departamento del usuario
select d.dep_id, d.dep_nombre from departamento d 
INNER JOIN cuidad c ON c.ciu_departamento = d.dep_id 
INNER JOIN persona p ON p.per_ciudad = c.ciu_id 
INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = 'luis';

--- Obtener Pais del usuario
select pa.pai_id, pa.pai_nombre from pais pa  
INNER JOIN departamento d ON d.dep_pais = pa.pai_id 
INNER JOIN cuidad c ON c.ciu_departamento = d.dep_id 
INNER JOIN persona p ON p.per_ciudad = c.ciu_id 
INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = 'luis';

--- Obtener datos de medico
select m.med_n_tarjeta, m.med_profesion from medico m 
INNER JOIN usuario u ON m.med_usuario = u.usu_id 
INNER JOIN persona p ON u.usu_persona = p.per_id 
WHERE u.usu_login = 'fabio';

--- Obtener datos de paciente
select pa.pac_altura, pa.pac_peso, pa.pac_observaciones, pa.pac_grupo_sanguineo from paciente pa 
INNER JOIN usuario u ON pa.pac_usuario = u.usu_id 
INNER JOIN persona p ON u.usu_persona = p.per_id 
WHERE u.usu_login = 'maria';

SELECT *  FROM paciente;

-- Obtener grupo sanguineo paciente
SELECT gs.grusan_id, gs.grusan_nombre FROM grupo_sanguineo gs 
INNER JOIN paciente pa ON gs.grusan_id = pa.pac_grupo_sanguineo   
INNER JOIN usuario u ON pa.pac_usuario = u.usu_id WHERE u.usu_login = 'maria';

-- obtener todo los grupos sanguineos
SELECT gs.grusan_id, gs.grusan_nombre FROM grupo_sanguineo gs;

-- Obtener los tipos de afiliacion
SELECT taf.tafi_id, taf.tafi_nombre, taf.tafi_descripcion FROM tipo_afiliacion taf;
select * from tipo_afiliacion;

-- obtener tipo de afiliacion por id
SELECT taf.tafi_id, taf.tafi_nombre, taf.tafi_descripcion FROM tipo_afiliacion taf WHERE taf.tafi_id = 1;

--obtener afiliacion por id
SELECT afi.afi_id, afi.afi_codigo, afi.afi_paciente, afi.afi_tipo_afiliacion, afi.afi_eps, afi.afi_estado FROM afiliacion afi WHERE afi.afi_id = 1;
select * from afiliacion;

-- Buscar si la afiliacion existe
SELECT * FROM afiliacion;
select  * from paciente;

SELECT COUNT(*) as cant FROM afiliacion afi 
INNER JOIN paciente pac ON pac.pac_id = afi.afi_paciente 
INNER JOIN usuario u ON u.usu_id = pac.pac_usuario 
WHERE afi.afi_codigo = '12131433' 
OR (afi_paciente = (SELECT pa.pac_id FROM paciente pa INNER JOIN usuario usu ON usu.usu_id = pa.pac_usuario WHERE usu.usu_id = 17) AND afi_eps = 2 AND afi_estado IN (1, 2));

SELECT *  FROM usuario;

-- Buscar existencia de paciente por id de usuario

SELECT COUNT(*) FROM paciente pa INNER JOIN usuario usu ON usu.usu_id = pa.pac_usuario WHERE usu.usu_id = 3;


-- Buscar pacientes para afiliar
SELECT * FROM persona p INNER JOIN usuario u ON u.usu_persona = p.per_id 
INNER JOIN paciente pa ON pa.pac_usuario = u.usu_id 
INNER JOIN grupo_sanguineo gs ON gs.grusan_id = pa.pac_grupo_sanguineo 
INNER JOIN rol_usuario ru ON ru.rolusu_usuario = u.usu_id 
INNER JOIN rol r ON ru.rolusu_rol = r.rol_id 
WHERE u.usu_estado IN (1, 2) AND r.rol_id = 3 AND ru.rolusu_estado IN (1, 2)
AND pa.pac_id  NOT IN (
SELECT pac.pac_id FROM paciente pac 
RIGHT JOIN afiliacion af ON pac.pac_id = af.afi_paciente WHERE af.afi_estado IN (1, 2)
);

-- Buscar si el paciente tiene afiliaciones
SELECT COUNT(*) FROM afiliacion afi 
INNER JOIN paciente pac ON pac.pac_id = afi.afi_paciente 
INNER JOIN usuario u ON u.usu_id = pac.pac_usuario 
INNER JOIN rol_usuario ru ON ru.rolusu_usuario = u.usu_id 
INNER JOIN rol r ON ru.rolusu_rol = r.rol_id  
WHERE u.usu_estado IN (1, 2) AND r.rol_id = 3 AND ru.rolusu_estado IN (1, 2) AND u.usu_id = 17


select *  from afiliacion;

-- Buscar si el paciente existe y no esta eliminado
SELECT COUNT(*) FROM paciente pa 
INNER JOIN usuario u ON u.usu_id = pa.pac_usuario 
INNER JOIN rol_usuario ru ON ru.rolusu_usuario = u.usu_id 
INNER JOIN rol r ON ru.rolusu_rol = r.rol_id  
WHERE u.usu_estado IN (1, 2) AND r.rol_id = 3 AND ru.rolusu_estado IN (1, 2) AND u.usu_id = 17

-- Buscar existencia eps y que no este eliminada
SELECT COUNT(*) as cant FROM eps ep WHERE ep.eps_id =3 AND ep.eps_estado IN (1, 2);

--- Buscar si la eps a eliminar tiene contratos
SELECT COUNT(*) FROM contrato con 
INNER JOIN eps e ON e.eps_id = con.con_eps  
WHERE con.con_estado IN (1, 2) AND e.eps_id = 4

--- Buscar si la eps a eliminar tiene afiliaciones
SELECT COUNT(*) FROM afiliacion afi 
INNER JOIN eps e ON e.eps_id = afi.afi_eps 
WHERE afi.afi_estado IN (1, 2) AND e.eps_id = 4

--- ELIMINAR CONTRATOS DE LA EPS
UPDATE contrato c SET con_estado = 3 
FROM eps e WHERE e.eps_id = c.con_eps AND e.eps_id = 4

--- ELIMINAR AFILIACIONES DE LA EPS
UPDATE afiliacion afi SET afi_estado = 3 
FROM eps e WHERE e.eps_id = afi.afi_eps AND e.eps_id = 4

-- ELIMINAR AFILIACIONES DEL PACIENTE
UPDATE afiliacion afi SET afi_estado = 3
FROM paciente pa, usuario u 
WHERE pa.pac_id = afi.afi_paciente AND u.usu_id = pa.pac_usuario AND u.usu_id = 17

--- LISTAR USUARIOS NO PACIENTES PERO NO ELIMINADOS
SELECT * FROM persona p 
INNER JOIN usuario u ON u.usu_persona = p.per_id 
INNER JOIN rol_usuario ru ON ru.rolusu_usuario = u.usu_id 
INNER JOIN rol r ON ru.rolusu_rol = r.rol_id 
WHERE u.usu_estado IN (1, 2) AND ru.rolusu_estado IN (1,2) 
AND u.usu_id NOT IN (
SELECT u.usu_id FROM rol_usuario ru, rol r, usuario u RIGHT JOIN paciente pa ON u.usu_id = pa.pac_usuario 
WHERE ru.rolusu_usuario = u.usu_id AND ru.rolusu_rol = r.rol_id AND u.usu_estado IN (1, 2) AND r.rol_id = 3 AND ru.rolusu_estado IN (1,2) 
)

-- Verificar que ya tenga el rol pero que no este eliminado
SELECT COUNT(*) FROM rol r 
INNER JOIN rol_usuario ru ON ru.rolusu_rol = r.rol_id 
INNER JOIN usuario u ON u.usu_id = ru.rolusu_usuario 
WHERE u.usu_login = 'pepe' AND u.usu_id = 17 AND r.rol_id = 3 AND u.usu_estado IN (1, 2) AND ru.rolusu_estado IN (1, 2);

-- Listar pacientes
SELECT * FROM persona p 
INNER JOIN usuario u ON u.usu_persona = p.per_id 
INNER JOIN paciente pa ON pa.pac_usuario = u.usu_id 
INNER JOIN grupo_sanguineo gs ON gs.grusan_id = pa.pac_grupo_sanguineo 
INNER JOIN rol_usuario ru ON ru.rolusu_usuario = u.usu_id 
INNER JOIN rol r ON ru.rolusu_rol = r.rol_id 
WHERE u.usu_estado IN (1, 2) AND r.rol_id = 3 AND ru.rolusu_estado IN (1, 2)

--- validar si con el id de usuario aparece en la tabla paciente

SELECT COUNT(*) FROM paciente pa 
INNER JOIN usuario u ON u.usu_id = pa.pac_usuario 
WHERE u.usu_id = 17 AND u.usu_estado IN (1, 2);

-- VALIDANDO SI EXISTE EL REGISTRO DE ROL PACIENTE ELIMINADO PARA ESE USUARIO
SELECT COUNT(*) FROM rol r 
INNER JOIN rol_usuario ru ON ru.rolusu_rol = r.rol_id 
INNER JOIN usuario u ON u.usu_id = ru.rolusu_usuario 
WHERE u.usu_id = 17 AND r.rol_id = 3 AND u.usu_estado IN (1, 2) AND ru.rolusu_estado = 3;

--- REACTIVANDO ROL PACIENTE PARA ESE USUARIO
UPDATE rol_usuario SET rolusu_estado = 1 
FROM rol_usuario ru, rol r, usuario u  
WHERE r.rol_id = ru.rolusu_rol AND u.usu_id = ru.rolusu_usuario 
AND u.usu_id = 17 AND r.rol_id = 3 AND u.usu_estado IN (1, 2) AND ru.rolusu_estado = 3;


---- Listar medicos para suscribir
SELECT * FROM persona p 
INNER JOIN usuario u ON u.usu_persona = p.per_id 
INNER JOIN medico med ON med.med_usuario = u.usu_id 
INNER JOIN rol_usuario ru ON ru.rolusu_usuario = u.usu_id 
INNER JOIN rol r ON ru.rolusu_rol = r.rol_id 
WHERE u.usu_estado IN (1, 2) AND r.rol_id = 2 AND ru.rolusu_estado IN (1, 2) 
AND med.med_id  NOT IN ( 
SELECT me.med_id FROM medico me 
RIGHT JOIN suscripcion sus ON me.med_id = sus.sus_medico WHERE sus.sus_estado IN (1, 2) ) 





WHERE pa.pac_id = afi.afi_paciente AND u.usu_id = pa.pac_usuario AND u.usu_id = 17


WHERE u.usu_id = 17 AND r.rol_id = 3 AND u.usu_estado IN (1, 2) AND ru.rolusu_estado = 3;



SELECT COUNT(*) FROM rol r 
INNER JOIN rol_usuario ru ON ru.rolusu_rol = r.rol_id 
INNER JOIN usuario u ON u.usu_id = ru.rolusu_usuario 
WHERE u.usu_id = 17 AND r.rol_id = 3 AND u.usu_estado IN (1, 2) AND ru.rolusu_estado = 3;


select *  from rol;

select * from paciente;
select *  from eps;


--- Cargar afiliacion del usuario paciente 
SELECT afi.afi_id, afi.afi_paciente, afi.afi_eps, afi.afi_estado, afi.afi_tipo_afiliacion,afi.afi_codigo from afiliacion afi 
INNER JOIN paciente pac ON pac.pac_id = afi.afi_paciente 
INNER JOIN usuario u ON u.usu_id = pac.pac_usuario 
INNER join eps ep ON ep.eps_id = afi.afi_eps 
WHERE u.usu_estado IN (1, 2) AND u.usu_id = 17

SELECT *  FROM PACIENTE;

-- existe asignacion ?
SELECT COUNT(*) as cant FROM asignacion asig 
INNER JOIN paciente pac ON pac.pac_id = asig.asig_paciente 
INNER JOIN usuario u ON u.usu_id = pac.pac_usuario 
WHERE asig_paciente = (SELECT pa.pac_id FROM paciente pa INNER JOIN usuario usu ON usu.usu_id = pa.pac_usuario WHERE usu.usu_id = 2) 
AND asig_medico = 2 AND asig_estado IN (1, 2);

select * from asignacion;




--- Buscamos si el medico esta suscrito a la eps del paciente
SELECT COUNT(*) FROM suscripcion sus 
INNER JOIN eps ep ON ep.eps_id = sus.sus_eps 
INNER JOIN medico me ON me.med_id = sus.sus_medico 
INNER JOIN estado e ON e.est_id = sus.sus_estado
WHERE ep.eps_id = 1 AND me.med_id = 1 AND sus.sus_estado IN (1, 2);


SELECT *  FROM suscripcion;

INNER JOIN rol_usuario ru ON ru.rolusu_rol = r.rol_id 
INNER JOIN usuario u ON u.usu_id = ru.rolusu_usuario 
WHERE u.usu_id = 17 AND r.rol_id = 3 AND u.usu_estado IN (1, 2) AND ru.rolusu_estado = 3;









-------------------------------------  ADMINISTRADOR ----- ADMINISTRACION DE USUARIOS ---------------------------------------------------

-- lista todos los usuario
SELECT p.per_id, p.per_identificacion, p.per_nombres, p.per_apellidos, p.per_email, u.usu_login
FROM persona p INNER JOIN usuarios_login u ON u.usu_persona = p.per_id;


-- busca el usuario por id
SELECT p.per_id, p.per_identificacion, p.per_nombres, p.per_apellidos, p.per_email, u.usu_login
FROM persona p INNER JOIN usuarios_login u ON u.usu_persona = p.per_id WHERE p.per_id = 1;

-- busca el usuario por Login
SELECT p.per_id, p.per_identificacion, p.per_nombres, p.per_apellidos, p.per_email, u.usu_login  
FROM persona p INNER JOIN usuarios_login u ON u.usu_persona = p.per_id WHERE u.usu_login = 'luis';

-- Busca usuarios por email
SELECT p.per_id, p.per_identificacion, p.per_nombres, p.per_apellidos, p.per_email, u.usu_login 
FROM persona p INNER JOIN usuarios_login u ON u.usu_persona = p.per_id WHERE p.per_email = 'luis.espinosa@usco.edu.co';

-- inserta nueva persona
INSERT INTO persona (per_identificacion, per_tipo_identificacion, per_nombres, per_apellidos, per_fecha_nacimiento, per_ciudad, per_email, per_telefono, per_direccion, per_genero, per_estado)
VALUES ('35124214', 1, 'Pedro Fernando', 'Perez', '1964-10-21', 1, 'prueba@gmail.com', '3224587898', 'Cra 43 N 17-24', 2, 1);

-- Luego de insertar la persona, se inserta el usuario y se asocia a la persona, pero primero se verifica que tipo de usuario es.
INSERT INTO usuario_paciente (usu_login, usu_password, usu_persona, usu_perfil)
VALUES ('pedro', '$2a$12$XCcwFnjwP0jIAwSAnk0emO.HRssWamr8cOqS3bm8SzLL98KiroUGa', 5, 'ROLE_PACIENTE');


-- Modificar la informacion de la persona - usuario
UPDATE persona SET per_identificacion = '1075284097', per_tipo_identificacion = 1, per_nombres = 'LUIS HERNAN', 
per_apellidos = 'ESPINOSA LLANOS', per_fecha_nacimiento = '1994-11-03', per_ciudad = 1, per_email = 'luis.espinosa@usco.edu.co', 
per_telefono = '3229457226', per_direccion = 'Cra 86 N 14-55', per_genero = 2, per_estado = 1 
FROM usuario WHERE usuario.usu_id = 1 AND usuario.usu_persona = persona.per_id;

-- Buscar el estado eliminado
select e.est_id from estado e WHERE e.est_nombre = 'ELIMINADO';

--- Eliminar usuario
UPDATE persona SET per_estado = 1 
FROM usuario WHERE usuario.usu_id = 1 AND usuario.usu_persona = persona.per_id;

select *  from persona;
select *  from estado;

-- Obtener Id de la persona
SELECT p.per_id FROM persona p 
INNER JOIN usuario u ON u.usu_persona = p.per_id 
WHERE u.usu_id = 1

-- Eliminar la cuenta de usuario
UPDATE persona SET per_estado = 3 WHERE per_id = 2;

------ JQUERY DATATABLES -----
-- Numero de usuarios (CONSULTA GENERAL)
SELECT COUNT(*) as cant FROM persona p INNER JOIN usuarios_login u ON u.usu_persona = p.per_id WHERE p.per_estado IN (1, 2)
AND (p.per_identificacion LIKE '1075284097' OR p.per_nombres LIKE 'luis' OR p.per_apellidos LIKE 'Llanos' OR u.usu_login LIKE 'luis');

--- Consulta para Datatables de Usuarios

SELECT per_id, per_identificacion, per_nombres, per_apellidos, usu_login  
from (select row_number() over(order by per_nombres DESC) AS RowNumber, 
p.per_id, p.per_identificacion, p.per_nombres, p.per_apellidos, u.usu_login FROM persona p 
LEFT JOIN usuarios_login u ON (u.usu_persona = p.per_id) 
WHERE p.per_estado IN (1, 2) 
AND (p.per_identificacion LIKE '' OR p.per_nombres LIKE '%Luis%' OR p.per_apellidos LIKE '' 
OR u.usu_login LIKE '' )) as tabla where tabla.RowNumber between 1 and 2

-- estados de la base de datos
SELECT e.est_id, e.est_nombre, e.est_descripcion FROM estado e;

-- Estados por entidad
SELECT est.est_id, est.est_nombre FROM estado est
INNER JOIN estado_entidad ee ON ee.ee_estado = est.est_id
INNER JOIN entidad e ON e.ent_id = ee.ee_entidad 
WHERE e.ent_nombre = 'medico';


-- Usuarios No Medicos
SELECT p.per_nombres 
FROM persona p 
INNER JOIN usuario u ON u.usu_persona = p.per_id 
INNER JOIN rol_usuario ru ON ru.rolusu_usuario = u.usu_id  
INNER JOIN rol r ON ru.rolusu_rol = r.rol_id 
WHERE p.per_estado IN (1, 2) AND ru.rolusu_estado IN (1,2) 
AND u.usu_id NOT IN (
SELECT u.usu_id FROM usuario u 
RIGHT JOIN medico m ON u.usu_id = m.med_usuario)


SELECT * FROM usuario 
WHERE usu_id NOT IN (
SELECT usu_id FROM usuario u 
RIGHT JOIN medico m ON u.usu_id = m.med_usuario)

-- Usuarios no  Administradores
SELECT DISTINCT u.usu_id, p.per_identificacion, p.per_nombres, p.per_apellidos, u.usu_login --COUNT(*) -- DISTINCT    ---p.per_nombres 
FROM persona p 
INNER JOIN usuario u ON u.usu_persona = p.per_id 
INNER JOIN rol_usuario ru ON ru.rolusu_usuario = u.usu_id  
INNER JOIN rol r ON ru.rolusu_rol = r.rol_id 
WHERE p.per_estado IN (1, 2) AND ru.rolusu_estado IN (1,2) 
AND u.usu_id NOT IN (
SELECT u.usu_id FROM usuario u 
RIGHT JOIN administrador a ON u.usu_id = a.adm_usuario)

select * from administrador;
select * from persona;


SELECT * FROM usuario 
WHERE usu_id NOT IN (
SELECT usu_id FROM usuario u 
RIGHT JOIN medico m ON u.usu_id = m.med_usuario)


--- Verificar si el usuario tiene el rol
SELECT COUNT(*) FROM rol r 
INNER JOIN rol_usuario ru ON ru.rolusu_rol = r.rol_id 
INNER JOIN usuario u ON u.usu_id = ru.rolusu_usuario 
WHERE u.usu_login = 'luis' AND u.usu_id = 1 AND r.rol_id IN (1);

--INSERTAR un nuevo rol para un usuario
INSERT INTO rol_usuario (rolusu_rol, rolusu_usuario, rolusu_estado) VALUES (1, 2, 1);
select * from rol_usuario;

-- insertar en la tala administrador
SELECT *  FROM administrador;
INSERT INTO administrador (adm_usuario, adm_observaciones) VALUES (2, 'muy buenas');

-- Buscar si existe el usuario
SELECT COUNT(*) as cant FROM usuario u WHERE u.usu_id = 1;

--- Actualizar datos de administrador
UPDATE administrador SET adm_observaciones = 'El administrador Luis se e delegan responsabilidades gestionar usuarios y medicos modi' 
FROM usuario WHERE usuario.usu_id = 1;

--- Eliminar un administrador
UPDATE rol_usuario SET rolusu_estado = 3 WHERE rolusu_usuario = 14 AND rolusu_rol = 1;
SELECT *  FROM rol_usuario;

-- Modificar familiar
UPDATE familiar SET fam_observaciones = 'Nuevo mensaje' WHERE fam_usuario = 15;



---Estraer datos propios del administrador
select a.adm_observaciones from administrador a 
INNER JOIN usuario u ON a.adm_usuario = u.usu_id 
INNER JOIN persona p ON u.usu_persona = p.per_id 
WHERE u.usu_login = 'luis';

SELECT * 
FROM persona p  
INNER JOIN usuario u ON u.usu_persona = p.per_id 
--INNER JOIN medico m ON m.med_usuario = u.usu_id 
INNER JOIN rol_usuario ru ON ru.rolusu_usuario = u.usu_id 
INNER JOIN rol r ON ru.rolusu_rol = r.rol_id 
WHERE p.per_estado IN (1, 2) AND r.rol_id != 2;










SELECT * FROM rol_persona p


-- el rol_id 2 es Role_medico
--SELECT * FROM usuario


-- Consultar Si el Perfil ya lo tiene el Usuario
SELECT COUNT(*) FROM usuarios_login u WHERE u.usu_login = 'fabio' AND u.usu_perfil = 'ROLE_ADMINISTRADOR';

SELECT *  FROM 

select *  from usuarios_login;
select * from persona;
select *  from estado;


--- ************  Hay que verificar ************
--- que el usuario o la persona no exista --- solo tiene la oportunidad de crear un usuario con varios roles
--- que no agrege un rol a su perfil que ya tenga


select *  from estado;
select *  from genero;
select *  from persona;
select *  from usuario_paciente;
delete from persona WHERE per_id in (4, 5);

-- //////// MODELO JSON PARA AGREGAR USUARIO  ---   http://localhost:8080/monitoreo/api/usuarios  ////////

{
	"identificacion":"11111111",
	"nombre":"Juan","apellido":"Garzon",
	"email":"juan@gmail.com",
	"ciudad":{
		"id":1,
		"nombre":"neiva",
			"departamento":	{
				"id":1,
				"nombre":"huila",
				"pais":	{
					"id":1,
					"nombre":"colombia"}
				}
	},
	"fechaNacimiento":"1965-08-22",
	"telefono":"3121111111",
	"direccion":"calle 11",
	"tipoIdentificacion":{
		"id":1,
		"nombre":"cédula de ciudadanía",
		"acronimo":"CC"
	},
	"estado":{
		"id":1,
		"nombre":"activo"
	},
	"genero":{
		"id":2,
		"nombre":"masculino",
		"acronimo":"M"
	},
	"password":"123456",
	"perfiles":[
		{"rol":"PACIENTE"}
	],
	"username":"juan"
}

--- ///////////////////////////////////////////////////



---------------------------------------------------------------------------------------------------------------------------------------------------------









-- validar inscripcion con correo del usuario
UPDATE persona SET email_verificado = 1 WHERE per_id = 4


-- organizaciones (peces) de cada usuario
SELECT p.per_id, p.per_nombres, p.per_apellidos, p.est_id, p.per_correo, o.org_nombre FROM persona p
INNER JOIN persona_organizacion po ON po.per_id = p.per_id
INNER JOIN organizacion o ON o.org_id = po.org_id
INNER JOIN usuario_administrador ua ON ua.per_id = p.per_id
INNER JOIN estado e ON e.est_id = o.est_id
WHERE ua.login = 'LuisLlanos' and e.est_nombre != 'eliminado';




-- tipos de identificacion
SELECT ti.ti_id, ti.ti_nombre, ti.ti_acronimo FROM tipo_identificacion ti
INNER JOIN persona p ON p.ti_id = ti.ti_id 
INNER JOIN usuario_administrador ua ON ua.per_id = p.per_id
WHERE ua.login = 'LuisLlanos';

SELECT ti.ti_id, ti.ti_nombre, ti.ti_acronimo FROM tipo_identificacion ti

-- estado del usuario
SELECT e.est_id, e.est_nombre, e.est_descripcion FROM estado e
INNER JOIN persona p ON p.est_id = e.est_id 
INNER JOIN usuario_administrador ua ON ua.per_id = p.per_id
WHERE ua.login = 'LuisLlanos';

select * from estado;

-- estado de cada organizacion
SELECT e.est_id, e.est_nombre, e.est_descripcion FROM estado e
INNER JOIN organizacion o ON o.est_id = e.est_id
WHERE o.org_id = 2;


INNER JOIN estado e ON e.est_id = o
INNER JOIN usuario_administrador ua ON ua.per_id = p.per_id
WHERE ua.login = 'LuisLlanos'

-- buscar organizacion por id
SELECT o.org_id, o.org_nombre, o.org_descripcion FROM organizacion o
WHERE o.org_id = 6;

-- buscar organizacion por nombre 
SELECT o.org_id, o.org_nombre, o.org_descripcion FROM organizacion o
WHERE o.org_nombre = 6;

-- insertar organizacion
INSERT INTO organizacion (org_nombre, org_descripcion) VALUES ('nueva', 'nueva');
select *  from organizacion order by org_id;
select *  from persona;
select *  from persona_organizacion;

delete from persona_organizacion WHERE po_id in (5, 6, 7, 8, 9, 10);
delete from organizacion WHERE org_id in (17, 18, 19, 20, 21, 22);
select setval ('organizacion_org_id_seq', 4);
select setval ('persona_organizacion_po_id_seq', 4);
SELECT * FROM estado;

-- insertar (asignar) organizacion a el usuario que la agrego
INSERT INTO persona_organizacion (per_id, org_id) VALUES (1, 5);

-- actualizar organizacion
UPDATE organizacion SET org_nombre = 'Organizacion Prueba 7 Modificada', org_descripcion = 'lo mismo', est_id = 2  WHERE org_id = 11;

-- cambiar estado de la organizacion
UPDATE organizacion SET est_id = 2 WHERE org_id = 10;

-- Buscar estao por nombre
SELECT e.est_id, e.est_nombre, e.est_descripcion FROM estado e
WHERE e.est_nombre = 'activo';


select *  from departamento
-- sedes (peces) de cada usuario 
SELECT o.org_id, o.org_nombre, o.est_id, o.org_descripcion, so.sedeorg_id, so.sedeorg_nombre, so.sedeorg_direccion, so.ciu_id, ciu.ciu_nombre, ciu.dep_id, dep.dep_nombre, so.est_id, so.coor_id, coor.coor_latitud, coor.coor_longuitud
FROM persona p
INNER JOIN persona_organizacion po ON po.per_id = p.per_id
INNER JOIN organizacion o ON o.org_id = po.org_id
INNER JOIN usuario_administrador ua ON ua.per_id = p.per_id
INNER JOIN sede_organizacion so ON so.org_id = o.org_id
INNER JOIN estado e ON e.est_id = so.est_id
INNER JOIN ciudad ciu ON ciu.ciu_id = so.ciu_id
INNER JOIN departamento dep ON dep.dep_id = ciu.dep_id
INNER JOIN coordenadas coor ON coor.coor_id = so.coor_id
WHERE ua.login = 'LuisLlanos' and e.est_nombre != 'eliminado'

-- estado de cada sede
SELECT e.est_id, e.est_nombre, e.est_descripcion FROM estado e
INNER JOIN sede_organizacion so ON so.est_id = e.est_id
WHERE so.sedeorg_id = 1;


-- espacios de las sedes del usuario
SELECT p.per_id, p.per_nombres, p.per_apellidos, p.est_id, esp.esp_nombre FROM persona p
INNER JOIN persona_organizacion po ON po.per_id = p.per_id
INNER JOIN organizacion o ON o.org_id = po.org_id
INNER JOIN usuario_administrador ua ON ua.per_id = p.per_id
INNER JOIN sede_organizacion so ON so.org_id = o.org_id
INNER JOIN espacio esp ON esp.sedeorg_id = so.sedeorg_id 
WHERE ua.login = 'LuisLlanos'

-- nodos de los espacios de las sedes del usuario
SELECT p.per_id, p.per_nombres, p.per_apellidos, p.est_id, esp.esp_nombre, nod.nod_nombre, tn.tn_nombre FROM persona p
INNER JOIN persona_organizacion po ON po.per_id = p.per_id
INNER JOIN organizacion o ON o.org_id = po.org_id
INNER JOIN usuario_administrador ua ON ua.per_id = p.per_id
INNER JOIN sede_organizacion so ON so.org_id = o.org_id
INNER JOIN espacio esp ON esp.sedeorg_id = so.sedeorg_id
INNER JOIN nodo nod ON nod.esp_id = esp.esp_id
INNER JOIN tipo_nodo tn ON nod.tn_id = tn.tn_id
WHERE ua.login = 'LuisLlanos'


-- dispositivos de los nodos de los espacios de las sedes de las organizaciones del usuario

SELECT p.per_id, p.per_nombres, p.per_apellidos, p.est_id, esp.esp_nombre, nod.nod_nombre, tn.tn_nombre, td.td_nombre, dis.disp_codigo FROM persona p
INNER JOIN persona_organizacion po ON po.per_id = p.per_id
INNER JOIN organizacion o ON o.org_id = po.org_id
INNER JOIN usuario_administrador ua ON ua.per_id = p.per_id
INNER JOIN sede_organizacion so ON so.org_id = o.org_id
INNER JOIN espacio esp ON esp.sedeorg_id = so.sedeorg_id
INNER JOIN nodo nod ON nod.esp_id = esp.esp_id
INNER JOIN tipo_nodo tn ON nod.tn_id = tn.tn_id
INNER JOIN nodo_dispositivo nd ON nd.nod_id = nod.nod_id
INNER JOIN dispositivo dis ON dis.disp_id = nd.disp_id
INNER JOIN tipo_dispositivo td ON td.td_id = dis.disp_id
WHERE ua.login = 'LuisLlanos'

-- medidas de los dispositivos de los nodos de los espacios de las sedes de las organizaciones del usuario

SELECT p.per_id, p.per_nombres, p.per_apellidos, p.est_id, esp.esp_nombre, nod.nod_nombre, tn.tn_nombre, td.td_nombre, dis.disp_codigo, m.med_nombre, 
te.tie_nombre, mnd.mnd_valor, mnd.mnd_fecha_hora FROM persona p
INNER JOIN persona_organizacion po ON po.per_id = p.per_id
INNER JOIN organizacion o ON o.org_id = po.org_id
INNER JOIN usuario_administrador ua ON ua.per_id = p.per_id
INNER JOIN sede_organizacion so ON so.org_id = o.org_id
INNER JOIN espacio esp ON esp.sedeorg_id = so.sedeorg_id
INNER JOIN nodo nod ON nod.esp_id = esp.esp_id
INNER JOIN tipo_nodo tn ON nod.tn_id = tn.tn_id
INNER JOIN nodo_dispositivo nd ON nd.nod_id = nod.nod_id
INNER JOIN dispositivo dis ON dis.disp_id = nd.disp_id
INNER JOIN tipo_dispositivo td ON td.td_id = dis.disp_id

INNER JOIN medicion_nodo_dispositivo mnd ON mnd.nd_id = nd.nd_id
INNER JOIN medicion m ON m.med_id = mnd.med_id
INNER JOIN tipo_escala te ON te.tie_id = m.tie_id
WHERE ua.login = 'LuisLlanos'

-- medidas 
-- Configurar la llave primaria-autoincrementable
select setval ('medicion_nodo_dispositivo_mnd_id_seq', 3);
delete from persona where per_id in (4);
select *  from persona







