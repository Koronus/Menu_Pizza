-- Cоздание таблиц

-- CREATE TABLE Types_of_dishes
-- (
--     code_type SERIAL PRIMARY KEY,
--     title_types_of_dishes text NOT NULL UNIQUE  
-- );

-- CREATE TABLE Components
-- (
--     code_component SERIAL PRIMARY KEY,
--     title text NOT NULL UNIQUE,                
--     calorie numeric(10,2) DEFAULT 100.00 CHECK (calorie > 0.99), 
--     price numeric(10,2) DEFAULT 50.00 CHECK (price > 0),         
--     weight numeric(10,2) DEFAULT 100.00 CHECK (weight > 0.99)     
-- );
-- CREATE TABLE Microelements
-- (
--     code_microelement SERIAL PRIMARY KEY,
--     title text NOT NULL UNIQUE  
-- );

-- CREATE TABLE Dishes
-- (
--     dishesID SERIAL PRIMARY KEY,
--     title text NOT NULL UNIQUE,                
--     price numeric(10,2) DEFAULT 0.00,           
--     code_type INTEGER NOT NULL,
--     FOREIGN KEY(code_type) REFERENCES Types_of_dishes(code_type)
-- );

-- CREATE TABLE Composition_dishes
-- (
--     dishesID integer,
--     code_component integer,
--     PRIMARY KEY(dishesID,code_component),
--     FOREIGN KEY(dishesID) REFERENCES Dishes(dishesID),
--     FOREIGN KEY(code_component) REFERENCES Components(code_component)
-- );

-- CREATE TABLE Composition_components
-- (
--     code_component integer,
--     code_microelement integer,
--     quantity_per_100 DECIMAL(10,2) DEFAULT 0.00 CHECK (quantity_per_100 >= 0),  
--     PRIMARY KEY(code_component,code_microelement),
--     FOREIGN KEY(code_component) REFERENCES Components(code_component),
--     FOREIGN KEY(code_microelement) REFERENCES Microelements(code_microelement)
-- );

-- CREATE TABLE Daily_recruitments
-- (
--     code_microelement INTEGER PRIMARY KEY,
--     quantity_in_mg numeric(10,2) DEFAULT 0.00 CHECK (quantity_in_mg >= 0), 
--     FOREIGN KEY(code_microelement) REFERENCES Microelements(code_microelement)
-- );


--Создание представлений
-- 1. VIEW по одной таблице
-- CREATE VIEW expensive_dishes AS
-- SELECT * FROM Dishes WHERE price > 1000;

-- 2. VIEW по нескольким таблицам
-- CREATE VIEW dish_details AS
-- SELECT 
--     d.dishesID,
--     d.title as dish_name,
--     d.price,
--     td.title_types_of_dishes as dish_type,
--     COUNT(cd.code_component) as components_count
-- FROM Dishes d
-- JOIN Types_of_dishes td ON d.code_type = td.code_type
-- LEFT JOIN Composition_dishes cd ON d.dishesID = cd.dishesID
-- GROUP BY d.dishesID, d.title, d.price, td.title_types_of_dishes;

-- -- 3. VIEW с GROUP BY и HAVING
CREATE VIEW high_calorie_components AS
SELECT 
    c.title,
    c.calorie,
    COUNT(cc.code_microelement) as microelements_count
FROM Components c
JOIN Composition_components cc ON c.code_component = cc.code_component
GROUP BY c.code_component, c.title, c.calorie
HAVING c.calorie > 500 AND COUNT(cc.code_microelement) > 3;


-- Создание индексов
--CREATE INDEX idx_dishes_title ON Dishes(title);
--CREATE INDEX idx_components_title ON Components(title);
--CREATE INDEX idx_dishes_price ON Dishes(price);
--CREATE INDEX idx_components_calorie ON Components(calorie);


--Создание тригеров
-- 1. Триггер для обновления цены блюда при изменении цен компонентов
-- CREATE OR REPLACE FUNCTION update_dish_price()
-- RETURNS TRIGGER AS $$
-- BEGIN
--     UPDATE Dishes d
--     SET price = (
--         SELECT SUM(c.price * cd.quantity) 
--         FROM Composition_dishes cd 
--         JOIN Components c ON cd.code_component = c.code_component 
--         WHERE cd.dishesID = NEW.dishesID
--     )
--     WHERE d.dishesID = NEW.dishesID;
--     RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;

-- CREATE TRIGGER trigger_update_dish_price
-- AFTER INSERT OR UPDATE OR DELETE ON Composition_dishes
-- FOR EACH ROW EXECUTE FUNCTION update_dish_price();

-- -- 2. Триггер для проверки калорийности
-- CREATE OR REPLACE FUNCTION check_calories()
-- RETURNS TRIGGER AS $$
-- BEGIN
--     IF NEW.calorie > 1000 THEN
--         RAISE NOTICE 'Высокая калорийность компонента: %', NEW.calorie;
--     END IF;
--     RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;

-- CREATE TRIGGER trigger_check_calories
-- BEFORE INSERT OR UPDATE ON Components
-- FOR EACH ROW EXECUTE FUNCTION check_calories();




