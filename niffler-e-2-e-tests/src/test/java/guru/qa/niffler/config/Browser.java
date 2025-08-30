package guru.qa.niffler.config;

import com.codeborne.selenide.SelenideConfig;

import javax.annotation.Nonnull;

public enum Browser {

    CHROME {
        public @Nonnull SelenideConfig getConfig() {
            return new SelenideConfig()
                .browser("chrome")
                .pageLoadStrategy("eager")
                .remote(Config.getInstance().selenoidUrl())
                .timeout(5000L);
        }
    },
    FIREFOX {
        public @Nonnull SelenideConfig getConfig() {
            return new SelenideConfig()
                .browser("firefox")
                .pageLoadStrategy("eager")
                .remote(Config.getInstance().selenoidUrl())
                .timeout(5000L);
        }
    };

    public abstract @Nonnull SelenideConfig getConfig();
}
