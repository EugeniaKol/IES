import random
import math
import matplotlib.pyplot as plt
import time

n = 8
max_amp = 1200

min_ticks = 1000
max_tick = 10000

delta_amp = max_amp/n

freqs = []
for i in range(1, n + 1):
    freq = delta_amp * i
    freqs.append(freq) 

times = []

for tic in range(min_ticks, max_tick+1, 100):
    amps = []
    phases = []

    for i in range(n):
        phase = []
        amp = []
        for j in range(tic):
            amp.append(random.random())
            phase.append(random.random())
        amps.append(amp)
        phases.append(phase)    

    signal_sum = []
    
    for t in range(tic):
        signals = []
        sum = 0
        for j in range(n):
            signal = (amps[j][t])* math.sin(freqs[j]*t+ phases[j][t])
            signals.append(signal)
        for s in signals:
            sum = sum + s
        signal_sum.append(sum)

    m = 0
    d = 0
    start_time = time.perf_counter()

    for s in signal_sum:
        m = m + s
    m = m/tic

    for s in signal_sum:
        d = d + math.pow((s - m), 2)
    d = d/tic
    
    end_time = time.perf_counter()
    times.append(end_time - start_time)
      

plt.plot(range(min_ticks, max_tick +1, 100), times, color = '#51074a')
plt.xlabel('час - кількість дискретних відліків') 
plt.ylabel('час обчислення')
plt.title('Графік залежності складності обчислень від часу') 
plt.show()