package com.timas.projects.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j;

@Log4j
@Data

public class Configuration {

    /* Фактор генерации, уменьшает в n раз первичные популяции */
    @JsonProperty
    int factor_generate = 100;

    /* задержка между рутинными операциями, ms */
    @JsonProperty
    long pause_routine = 1000;
    /* максимальное количество циклов мира от -1 до .... */
    @JsonProperty
    long max_tick = 1000;

    /* дефолтные размеры мира */
    @JsonProperty
    int sizeX = 10;

    @JsonProperty
    int sizeY = 10;

    /* рендер статистики каждые n секунд. 0 - не выводить */
    @JsonProperty
    int statistics_timeout = 10;

    /* рендерить статистику в консоль */
    @JsonProperty
    boolean statistics_console = true;

    /* Фактор ивентов */
    @JsonProperty
    int factor_event = 20;

    /* пауза между любыми ивентами, сек. 0 - ивенты не происходят никогда */
    @JsonProperty
    int event_timeout = 10;

    /* шанс того, что пойдет дождь */
    @JsonProperty
    int chance_rain = 30;

    //todo:: ивент пожара
    /* шанс пожара в клетке, уничтожает все живое в клетке с шансом chance_kill*/
    @JsonProperty
    int chance_fire = 5;

    @JsonProperty
    int chance_kill = 50;

    @JsonCreator
    public Configuration() {
    }


}
