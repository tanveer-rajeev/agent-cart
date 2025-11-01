CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE vector_store (
    id UUID PRIMARY KEY,
    content VARCHAR(250) NOT NULL,
    embedding VECTOR(1024),
    metadata JSONB,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_vector_store_embedding
  ON vector_store USING ivfflat (embedding vector_cosine_ops)
  WITH (lists = 100);