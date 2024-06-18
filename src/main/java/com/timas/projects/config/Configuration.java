package com.timas.projects.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
@Log4j
@RequiredArgsConstructor
@Data
public class Configuration {
    final String config_file;

    /* Фактор генерации, уменьшает в n раз первичные популяции */
    int factor_generate = 20;

    /* максимальное количество циклов мира от -1 до .... */
    long max_tick = 1000;

    /* дефолтные размеры мира */
    int sizeX = 20;
    int sizeY = 10;

    /* рендер статистики каждые n секунд. 0 - не выводить */
    int statistics_timeout = 10;

    /* рендерить статистику в консоль */
    boolean statistics_console = true;

    /* Фактор ивентов */
    int factor_event = 20;

    /* пауза между любыми ивентами, сек. 0 - ивенты не происходят никогда */
    int event_timeout = 10;
    /* шанс того, что пойдет дождь */
    int chance_rain = 100;

    //todo:: ивент пожара
    /* шанс пожара в клетке, уничтожает все живое в клетке с шансом chance_kill*/
    int chance_fire = 5;
    int chance_kill = 50;


    /*TODO:: метод чтения конфига из yaml фала config_file */

}
