package ni.co.nico.domain;

public class Leveling {
    public int calculateLevel(int score) {
        if (score <= 1) {
            return 1;
        } else if (score <= 500) {
            return 1 + (score - 1) / 50;
        } else if (score <= 1000) {
            return 11 + (score - 500) / 100;
        } else if (score <= 2000) {
            return 21 + (score - 1000) / 200;
        } else if (score <= 4000) {
            return 31 + (score - 2000) / 400;
        } else if (score <= 8000) {
            return 41 + (score - 4000) / 800;
        } else if (score <= 16000) {
            return 51 + (score - 8000) / 1600;
        } else if (score <= 32000) {
            return 61 + (score - 16000) / 3200;
        } else if (score <= 64000) {
            return 71 + (score - 32000) / 6400;
        } else if (score <= 128000) {
            return 81 + (score - 64000) / 12800;
        } else {
            return 91 + (score - 128000) / 25600;
        }
    }
}
