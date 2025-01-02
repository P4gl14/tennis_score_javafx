package com.example.tennis;

public class Punteggio {

    private static final String[] PUNTEGGI_TENNIS = {"0", "15", "30", "40"};

    private int points;
    private int games;
    private int set;
    private boolean vantaggio;
    private boolean parità;

    public Punteggio() {
        reset();
    }

    public void aggiungiPunto(Punteggio avversario) {
        if (parità) {
            gestisciParità(avversario);
            return;
        }

        if (vantaggio) {
            vinciGioco();
            return;
        }

        points++;
        if (points >= 4 && points - avversario.points >= 2) {
            vinciGioco();
        } else if (points >= 3 && points == avversario.points) {
            parità = true;
        }
    }

    private void gestisciParità(Punteggio avversario) {
        if (vantaggio) {
            vinciGioco();
        } else if (avversario.vantaggio) {
            avversario.vantaggio = false;
        } else {
            vantaggio = true;
        }
    }

    private void vinciGioco() {
        games++;
        points = 0;
        parità = false;
        vantaggio = false;

        if (games >= 6 && games - set >= 2) {
            vinciSet();
        }
    }

    private void vinciSet() {
        set++;
        games = 0;
    }

    public void reset() {
        points = 0;
        games = 0;
        set = 0;
        vantaggio = false;
        parità = false;
    }

    public String getPunti(int pointsAvversario) {
        if (parità) return "DEUCE";
        if (vantaggio) return "ADV";
        return points < PUNTEGGI_TENNIS.length ? PUNTEGGI_TENNIS[points] : "40";
    }

    public int getGiochi() {
        return games;
    }

    public int getSet() {
        return set;
    }

    public boolean isParità() {
        return parità;
    }

    public boolean isVantaggio() {
        return vantaggio;
    }
}
