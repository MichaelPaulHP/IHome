package com.example.mrrobot.ihome;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import androidx.benchmark.BenchmarkRule;
import androidx.benchmark.BenchmarkState;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.mrrobot.ihome", appContext.getPackageName());
    }
}
@RunWith(AndroidJUnit4.class)
class MyBenchmark {
    @Rule
    public BenchmarkRule benchmarkRule = new BenchmarkRule();

    @Test
    public void myBenchmark() {
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            Context appContext = InstrumentationRegistry.getTargetContext();

            assertEquals("com.example.mrrobot.ihome", appContext.getPackageName());
        }
    }
}