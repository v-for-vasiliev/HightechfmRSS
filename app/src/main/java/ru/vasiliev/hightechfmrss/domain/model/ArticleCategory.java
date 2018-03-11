package ru.vasiliev.hightechfmrss.domain.model;

/**
 * Created by vasiliev on 18/02/2018.
 */

public enum ArticleCategory {
    ALL(0), CASES(1), IDEAS(2), BLOCKCHAIN(3), OPINIONS(4), TRENDS(5);

    private int id;

    ArticleCategory(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        switch (of(id)) {
            case CASES:
                return "Кейсы";
            case IDEAS:
                return "Идеи";
            case BLOCKCHAIN:
                return "Блокчейн";
            case OPINIONS:
                return "Мнения";
            case TRENDS:
                return "Тренды";
            case ALL:
            default:
                return "Новости";
        }
    }

    public static ArticleCategory of(int id) {
        for (ArticleCategory status : ArticleCategory.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return ALL;
    }

    public static ArticleCategory of(String rssValue) {
        if (rssValue.contains("Кейсы")) {
            return CASES;
        }
        if (rssValue.contains("Идеи")) {
            return IDEAS;
        }
        if (rssValue.contains("Блокчейн")) {
            return BLOCKCHAIN;
        }
        if (rssValue.contains("Мнения")) {
            return OPINIONS;
        }
        if (rssValue.contains("Тренды")) {
            return TRENDS;
        }
        return ALL;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}