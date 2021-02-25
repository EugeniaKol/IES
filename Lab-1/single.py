import random
import math
import matplotlib.pyplot as plt
import time

n = 8
tics = 1024
max_amp = 1200
delta_amp = max_amp/n

freqs = []

for i in range(1, n + 1):
    freq = delta_amp * i
    freqs.append(freq) 

amps = []
phases = []

for i in range(n):
    phase = []
    amp = []
    for j in range(tics):
        amp.append(random.random())
        phase.append(random.random())
    amps.append(amp)
    phases.append(phase)    

signal_sum = []


for t in range(tics):
    signals = []
    sum = 0
    for j in range(n):
        signal = (amps[j][t])* math.sin(freqs[j]*t+ phases[j][t])
        signals.append(signal)
    for s in signals:
        sum = sum + s
    
    signal_sum.append(sum)


plt.plot(range(1, tics + 1), signal_sum, color = '#E84A5F')
plt.xlabel('час - дискретні відліки') 
plt.ylabel('сигнал')
plt.title('Графік залежності згенерованого сигналу від часу') 

m = 0
d = 0

start_time = time.perf_counter()

for s in signal_sum:
     m = m + s
m = m/tics

for s in signal_sum:
     d = d + math.pow((s - m), 2)
d = d/tics

end_time = time.perf_counter()

print('Average: ', m)
print('Standart deviation: ',d)
print('Time elapsed: ', end_time - start_time)
plt.show()