/*
 Navicat Premium Data Transfer

 Source Server         : scala_play_bitcoin_price_forecasting
 Source Server Type    : PostgreSQL
 Source Server Version : 100004
 Source Host           : localhost:5432
 Source Catalog        : bao.ho
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100004
 File Encoding         : 65001

 Date: 23/08/2018 19:26:39
*/


-- ----------------------------
-- Table structure for currencies
-- ----------------------------
DROP TABLE IF EXISTS "public"."currencies";
CREATE TABLE "public"."currencies" (
  "id" int4 NOT NULL DEFAULT nextval('currencies_id_seq'::regclass),
  "currency" char(20) COLLATE "pg_catalog"."default" NOT NULL,
  "abbreviation" char(5) COLLATE "pg_catalog"."default" NOT NULL,
  "currency_type" char(6) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."currencies" OWNER TO "bao.ho";

-- ----------------------------
-- Records of currencies
-- ----------------------------
BEGIN;
INSERT INTO "public"."currencies" VALUES (1, 'bitcoin             ', 'btc  ', 'crypto');
COMMIT;

-- ----------------------------
-- Primary Key structure for table currencies
-- ----------------------------
ALTER TABLE "public"."currencies" ADD CONSTRAINT "currencies_pkey" PRIMARY KEY ("id");
