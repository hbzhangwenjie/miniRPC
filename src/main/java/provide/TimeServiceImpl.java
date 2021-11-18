package provide;

import api.TimeService;

/**
 * @author jiimmy
 * @version 1.0
 * @date 2021/5/4 4:22 下午
 */

public class TimeServiceImpl implements TimeService {

    @Override
    public long getCurrentTimeMillis()  {
        return System.currentTimeMillis();
    }

    @Override
    public Long getCurrentTimeSec()  {
        return System.currentTimeMillis()/1000;
    }
}
