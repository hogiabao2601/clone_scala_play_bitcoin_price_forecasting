# --- !Ups

CREATE TABLE "public"."currencies" (
  "id" int4 NOT NULL DEFAULT nextval('currencies_id_seq'::regclass),
  "currency" char(10) COLLATE "pg_catalog"."default" NOT NULL,
  "abbreviation" char(5) COLLATE "pg_catalog"."default" NOT NULL,
  "currency_type" char(6) COLLATE "pg_catalog"."default" NOT NULL
);
ALTER TABLE "public"."currencies" ADD CONSTRAINT "currencies_pkey" PRIMARY KEY ("id");
INSERT INTO "public"."currencies" VALUES (1, 'bitcoin', 'btc', 'crypto');


CREATE TABLE "public"."currency_infos" (
  "timestamp" int8 NOT NULL,
  "currency_id" int4 NOT NULL,
  "market_cap_by_available_supply" int8,
  "price_usd" float4,
  "volume" int8
);
ALTER TABLE "public"."currency_infos" ADD CONSTRAINT "currency_infos_pkey" PRIMARY KEY ("timestamp");

# --- !Downs

DROP TABLE IF EXISTS "public"."currencies";
DROP TABLE IF EXISTS "public"."currency_infos";

