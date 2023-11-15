package com.example.Entities;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }
    public double dotProduct(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }
    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }
    public Vector2D normalize() {
        double magnitude = Math.sqrt(this.x * this.x + this.y * this.y);
        return new Vector2D(this.x / magnitude, this.y / magnitude);
    }
    public boolean equals(Vector2D other) {
        return this.x == other.x && this.y == other.y;
    }
}
