package by.integrator.telegrambot.model.enums;

public enum  BrandList {
    BRAND_ONE("Дмима Масленников", "https://www.youtube.com/channel/UCwipTluVS2mjuhPtx2WU7eQ"),
    BRAND_TWO("Эмиль", "https://www.youtube.com/channel/UCVlPyvH-XhOVPovpLe0EkMw"),
    BRAND_THREE("Супер Стас", "https://www.youtube.com/channel/UCK0iilUPnz6LXDF6EJpzGbA"),
    BRAND_FOUR("Великий Вася", "https://www.youtube.com/channel/UCvYXxjxs2lcjKxnP8dY5D1A"),
    BRAND_FIVE("Просто Масло", "https://www.youtube.com/channel/UCLLskD34Tpv3Y4j7DVQ_PMg");

    private String name;
    private String link;

    public String getName() {
        return name;
    }
    public String getLink() {
        return link;
    }

    BrandList(String name, String link) {
        this.name = name;
        this.link = link;
    }

}
