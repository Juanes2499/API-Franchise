CREATE DATABASE "franchisesDataBase";

CREATE SCHEMA "franchisesSchema";

CREATE TABLE "franchises" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(30) NOT NULL,
    "createdAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "branches" (
    "id" SERIAL PRIMARY KEY,
    "franchiseId" INT NOT NULL,
    "name" VARCHAR(30) NOT NULL,
    "address" VARCHAR(100) NOT NULL,
    "phoneNumber" VARCHAR(14) NOT NULL,
    "createdAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_branches_franchises FOREIGN KEY ("franchiseId") REFERENCES franchises("id") ON DELETE CASCADE
);

CREATE TABLE "products" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(30) NOT NULL,
    "description" VARCHAR(100) NOT NULL,
    "price" NUMERIC(10,2) NOT NULL,
    "createdAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE branches_products (
    "id" SERIAL PRIMARY KEY,
    "branchId" INT NOT NULL,
    "productId" INT NOT NULL,
    "stock" INT NOT NULL,
    "createdAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_branches FOREIGN KEY ("branchId") REFERENCES branches("id") ON DELETE CASCADE,
    CONSTRAINT fk_products FOREIGN KEY ("productId") REFERENCES products("id") ON DELETE CASCADE
);

-- Trigger para actualizar `updated_at` automáticamente
CREATE FUNCTION update_updated_at_column() 
RETURNS TRIGGER AS $$
BEGIN
    NEW."updatedAt" = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_branches
BEFORE UPDATE ON branches
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trigger_update_products
BEFORE UPDATE ON products
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trigger_update_branches_products
BEFORE UPDATE ON branches_products
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();


GaD7,%k8d$h2b5rQr(