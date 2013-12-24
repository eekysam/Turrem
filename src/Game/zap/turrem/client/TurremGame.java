package zap.turrem.client;

import zap.turrem.Turrem;
import zap.turrem.loader.TechLoader;
import zap.turrem.tech.TechList;
import zap.turrem.utils.StopTimer;

public class TurremGame implements ITurremGame
{
	public static final float tps = 40;
	public final int tickspace;

	private long mils;

	public Turrem theTurrem;

	private boolean running;

	private long tickcount = 0;

	public TurremGame(Turrem turrem)
	{
		this.theTurrem = turrem;
		this.tickspace = (int) (1000 / tps);
	}

	// TODO Could use improvements
	// TODO Learn how to thread
	@Override
	public void runGameLoop()
	{
		long diff = System.currentTimeMillis() - this.mils;
		if (diff >= this.tickspace || diff < 0)
		{
			this.mils = System.currentTimeMillis();
			this.runTick();
		}
	}

	public void runTick()
	{
		this.tickcount++;

		// TODO Program Game

		if (this.tickcount > 1000)
		{
			this.gotoMainMenu();
		}
	}

	public boolean isrunning()
	{
		return this.running;
	}

	@Override
	public void onStart()
	{
		StopTimer timer = new StopTimer();
		timer.start();
		this.doLoad();
		long nano = timer.end();
		System.out.printf("Loaded %d techs in %.2fms%n", TechList.techCount(), nano * 0.000001D);
	}

	public void doLoad()
	{
		try
		{
			TechLoader.loadJar(this.theTurrem.getDir() + "jars/");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		TechList.loadBranches();
	}

	public void gotoMainMenu()
	{
		this.theTurrem.gotoMenu();
		this.running = false;
	}

	@Override
	public void run()
	{
		this.running = true;

		try
		{
			this.onStart();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			this.gotoMainMenu();
			return;
		}

		try
		{
			while (this.running)
			{
				if (this.running)
				{
					try
					{
						this.runGameLoop();
					}
					catch (Throwable e)
					{
						e.printStackTrace();
						this.gotoMainMenu();
						return;
					}

					continue;
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			this.gotoMainMenu();
			return;
		}

		this.running = true;
	}
}